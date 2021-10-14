package com.example.freight.controller;

import com.example.freight.domain.Domains;
import com.example.freight.domain.FormObject;
import com.example.freight.domain.ResultData;
import com.example.freight.service.ITestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.http.HttpServletResponse;

import java.io.InputStream;



@RestController
@CrossOrigin
@RequestMapping("/file")
public class TestController {

    @Autowired
    ITestService testService;

    @Autowired
    CostController costController;

    /**
     * 接收前端文件，处理数据
     * */
    @RequestMapping(value = "/input", method = RequestMethod.POST)
    @ResponseBody
    public ResultData test(MultipartFile file) throws Exception {

        if(file.isEmpty()){
            return null;
        }else {
            String fileName = file.getOriginalFilename();//获取文件名
            InputStream in = file.getInputStream();//获取文件输入流

            ResultData resultData = new ResultData();
            try {
                resultData = testService.insertChoice(in, fileName);
            }catch (Exception e){
                resultData.setMsg(e.getMessage());
            }
            return resultData;
        }
    }

    /**
     * 根据现有数据生成excel表格并下载
     * */
    @RequestMapping("/downland")
    public void downland(@RequestBody FormObject form,HttpServletResponse response){
        testService.downland(form,response);
    }

    /**
     * 根据excel表格更新库存数据
     * */
    @RequestMapping(value = "/stock", method = RequestMethod.POST)
    @ResponseBody
    public ResultData updateStock(MultipartFile file) throws Exception {

        if(file.isEmpty()){
            return null;
        }else {
            String fileName = file.getOriginalFilename();//获取文件名
            InputStream in = file.getInputStream();//获取文件输入流
            ResultData resultData = new ResultData();
            try {
                resultData = testService.updateStock(in, fileName);
            }catch (Exception e){
                resultData.setMsg(e.getMessage());
            }
            return resultData;
        }
    }

    /**
     * 根据长字符串下载excel表格
     * */
    @RequestMapping("/getText")
    public void getText(String str,HttpServletResponse response){
        testService.getText(str,response);
    }


}
