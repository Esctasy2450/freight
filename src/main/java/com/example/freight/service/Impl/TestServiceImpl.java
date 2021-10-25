package com.example.freight.service.Impl;

import com.example.freight.controller.CostController;
import com.example.freight.domain.Domains;
import com.example.freight.domain.FormObject;
import com.example.freight.domain.ResultData;
import com.example.freight.mapper.ModelMapper;
import com.example.freight.service.ITestService;
import com.example.freight.tool.TypeTool;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: curry
 * @Date: 2018/8/16
 */
@Service
public class TestServiceImpl implements ITestService {

    @Autowired
    CostController costController;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ModelServiceImpl modelServiceImpl;

    /**
     * 根据文件获取结果
     */
    public ResultData insertChoice(InputStream excelFile, String fileName) throws Exception {

        Sheet sheet = this.getWorkbook(excelFile, fileName);

        FormObject formObject = new FormObject();

        try {
            //获取数据并判断是否安装
            formObject.setType(getIsType(sheet));

            //获取邮编
            formObject.setZipCode(getCode(sheet));

            //从工作表的，第四行开始，遍历获取数据，直到最后一行
            formObject.setDomains(getList(sheet));

            //将处理好的数据传递至前端页面
            ResultData resultData = new ResultData();
            resultData.setCode(1);
            resultData.setMsg("success");
            resultData.setData(formObject);
            //处理好的数据传给costController完成业务处理
            return resultData;
        } catch (Exception e) {
            throw new Exception("Please fill in the information completely and in the correct format");
        }
    }

    /**
     * 下载excel，并将原有数据填写完整
     */
    @Override
    public void downland(FormObject formObject, HttpServletResponse response) {

        // 创建workbook相当于创建一个excel文件
        XSSFWorkbook workbook = new XSSFWorkbook();
        // sheet相当于excel表格中下方的不同的表
        XSSFSheet sheet = workbook.createSheet();

        //初始化所需要的行
        int s = formObject.getDomains().size() + 4;
        Row[] row = new Row[s];
        for (int i = 0; i < s; i++) {
            row[i] = sheet.createRow(i);
        }

        int i = 0;
        //sssss为检测下载文件的操作，如果是批量导出，则输出下面文本，如果是匹配字符串，则跳过该步骤
        if (!(formObject.getZipCode().equals("sssss"))) {
            //初始化表格文件的 初始文本信息
            row[0].createCell(0).setCellValue("Assemble(Y/N)");
            row[0].createCell(1).setCellValue(formObject.isType() ? "Y" : "N");
            row[0].createCell(2).setCellValue("You can enter y or n to determine whether to select assembly，The default value is No");
            row[1].createCell(0).setCellValue("zipCode");
            //判断读取的邮编格式，如果以.0结尾，取.0之前， 如果数据格式正常，则正常输出
            row[1].createCell(1).setCellValue(formObject.getZipCode().endsWith(".0") ? formObject.getZipCode().substring(0, formObject.getZipCode().lastIndexOf(".")) : formObject.getZipCode());
            row[1].createCell(2).setCellValue("Enter the zip code of your destination");
            row[2].createCell(0).setCellValue("SKU");
            row[2].createCell(1).setCellValue("Quantity");
            row[3].createCell(2).setCellValue("Enter the model on the left and the quantity on the right，The default quantity is 1");
            //将i置为3，从第四行开始输出型号
            i = 3;
        }

        //从第四行开始，遍历输出型号和数量
        for (Domains domains : formObject.getDomains()) {
            row[i].createCell(0).setCellValue(domains.getSku());
            row[i].createCell(1).setCellValue(domains.getNum());
            i++;
        }

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode("new.xlsx", "UTF-8");
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            workbook.write(outputStream);
            outputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 批量更新库存
     * */
    @Override
    public ResultData updateStock(InputStream excelFile, String fileName) throws Exception {

        //获取工作表信息
        Sheet sheet = this.getWorkbook(excelFile, fileName);

        //定义变量接收读取数据
        String sku;
        int stock;
        List<Domains> list = new ArrayList<>();

        ResultData resultData = new ResultData();

        //遍历读取数据
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            sku = row.getCell(0).getCellTypeEnum().toString().equals("STRING") ? row.getCell(0).getStringCellValue() : String.valueOf((int) row.getCell(0).getNumericCellValue());
            stock = row.getCell(1).getCellTypeEnum().toString().equals("STRING") ? Integer.parseInt(row.getCell(1).toString()) : (int) row.getCell(1).getNumericCellValue();

            Domains domains = new Domains();
            domains.setSku(sku);
            //将文件读取的库存赋值为新库存
            domains.setNewStock(stock);
            list.add(domains);
        }

        //批量查询库存，1为上传文件更新标识，无需查询sku是否存在
        list = modelServiceImpl.selectModel(list,1);

        if (list.size() > 0) {
            resultData.setCode(1);
            resultData.setMsg("success");
            resultData.setData(list);
        }
        return resultData;
    }

    /**
     * 匹配字符串
     * */
    @Override
    public void getText(String str, HttpServletResponse response) {
        String sku = "class=\"itemColumn\">(.*?)</div>";
        String num = "class=\"field-quantity-inner\">(.*?)</div>";
        FormObject formObject = new FormObject();
        formObject.setDomains(getSubUtil(str, sku, num));
        formObject.setZipCode("sssss");
        downland(formObject, response);
    }

    /**
     * 检测文件格式是否正确
     */
    public Sheet getWorkbook(InputStream in, String fileName) throws Exception {

        Workbook workbook;
        Sheet sheet;

        if (fileName.endsWith(".xls")) {
            workbook = new HSSFWorkbook(in);
            sheet = workbook.getSheetAt(0);
        } else if (fileName.endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(in);
            sheet = workbook.getSheetAt(0);
        } else {
            throw new Exception("Please upload .xls /.xlsx format file!");
        }

        return sheet;
    }

    /**
     * 根据工作表的sheet，从获取 1B 的数据，判断是否组装
     */
    private boolean getIsType(Sheet sheet) {
        //获取第一行第二个数据，格式化模版里的 是否安装
        Row row = sheet.getRow(0);
        Cell typeValue = row.getCell(1);
        //判断型号 输出 布尔型
        return TypeTool.typeTool(typeValue.getStringCellValue());
    }

    /**
     * 根据工作表的sheet，从获取 2B 的邮编
     */
    private String getCode(Sheet sheet) {
        Row row = sheet.getRow(1);
        Cell codeValue = row.getCell(1);
        //判断邮编的数据类型，分别为文本或者数字，分别处理
        String s = codeValue.getCellTypeEnum().toString().equals("STRING") ? codeValue.getStringCellValue() : codeValue.toString();
        //去除结尾出 .0
        return s.endsWith(".0") ? s.substring(0, s.lastIndexOf(".")) : s;
    }

    /**
     * 根据工作表的sheet，从第四行开始获取 型号 和 数量
     */
    private List<Domains> getList(Sheet sheet) throws Exception {
        if (sheet.getRow(3).getCell(0) == null) {
            throw new Exception("SKU not detected");
        }

        Row row;
        List<Domains> list = new ArrayList<>();

        //从第四行开始，遍历获取数据，直到最后一行
        for (int i = 3; i <= sheet.getLastRowNum(); i++) {
            Domains domains = new Domains();
            row = sheet.getRow(i);
            //获取第一个数据为型号
            Cell skuValue = row.getCell(0);

            //判断邮编的数据类型，分别为文本或者数字，分别处理，前者为文本型转string,后者为 数值型转double转int转string
            String sku = skuValue.getCellTypeEnum().toString().equals("STRING") ? skuValue.getStringCellValue() : String.valueOf((int) skuValue.getNumericCellValue());
            //获取第二个数据为数量
            Cell numValue = row.getCell(1);

            int num;
            if (numValue == null) {
                //当数量读取为空时，默认设置为 1
                num = 1;
            } else {
                //判断邮编的数据类型，分别为文本或者数字，分别处理，前者为 文本型转string转int,后者为 数值型转double转int
                num = numValue.getCellTypeEnum().toString().equals("STRING") ? Integer.parseInt(numValue.toString()) : (int) numValue.getNumericCellValue();
            }

            domains.setSku(sku);
            domains.setNum(num);

            list.add(domains);
        }
        return list;
    }

    /**
     * 正则表达式匹配两个指定字符串中间的内容
     *
     * @param soap
     * @return
     */
    public static List<Domains> getSubUtil(String soap, String sku, String num) {
        List<Domains> list = new ArrayList<>();

        Pattern pattern = Pattern.compile(sku);// 匹配的模式
        Matcher m = pattern.matcher(soap);

        Pattern pattern1 = Pattern.compile(num);// 匹配的模式
        Matcher m1 = pattern1.matcher(soap);

        while (m.find() && m1.find()) {
            Domains domains = new Domains();

            if (!m.group(1).contains("-")) {
                break;
            }

            //字符串截取拆分
            domains.setSku(m.group(1));
            domains.setNum(Integer.parseInt(m1.group(1)));
            list.add(domains);
        }
        return list;
    }

}