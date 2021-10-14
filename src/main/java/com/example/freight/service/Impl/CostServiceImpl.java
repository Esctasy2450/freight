package com.example.freight.service.Impl;

import com.example.freight.domain.*;
import com.example.freight.mapper.CostMapper;
import com.example.freight.service.ICostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CostServiceImpl implements ICostService {

    @Autowired
    CostMapper costMapper;

    @Autowired
    CodeServiceImpl codeService;

    @Autowired
    ModelServiceImpl modelService;

    /**
     * 根据邮编，型号列表，是否组装，查询邮费信息
     */
    @Override
    public ResultData selectParameter(String zipCode, List<Domains> domains, boolean type) throws Exception {

        Map<String, Object> map = new HashMap<>();

        ResultData resultData = new ResultData();

        //检验邮编信息
        Code code = codeService.selectNameByZipCode(zipCode);
        //当目的地获取为空时，输出true，返回错误信息
        if (code.isNull()) {
            resultData.setCode(-1);
            resultData.setMsg(code.getDestination());
            return resultData;
        }

        //检查型号信息是否正确，0表示获取运费标识，sku不存在时，需要进行报错
        domains = modelService.selectModel(domains,0);

        //根据商品列表，获取总重量和总体积
        Model model = modelService.getVW(domains, type);

        //根据目的地，邮费档位,托盘数量，是否超长，计算邮费
        int s = manageDataS(model.getWeight());         //邮费档位
        int i = manageDataI((int) model.getVolume());   //托盘数量
        //邮费
        double cost = selectCost(code.getDestination(), s, i, model.isSize());

        //最终成功时，设置前端接收的参数
        map.put("cost", cost);                              //邮费
        //取总体积，换算单位
        double v = model.getVolume();                       //总体积
        map.put("ftVolume", String.format("%.2f", v * 0.0005787));                 //转换cubic ft，保留两位小数
        map.put("meterVolume", String.format("%.4f", v * 0.00001639));             //转换cubic meter，保留四位小数
        map.put("allWeight", model.getWeight());            //总重量
        map.put("i", i);                                    //托盘数量
        map.put("list", domains);                           //型号数据信息列表
        map.put("zipCode", zipCode);                        //邮编
        map.put("type", type);                              //是否组装

        resultData.setCode(1);
        //成功
        resultData.setMsg("Submitted successfully");
        resultData.setData(map);

        return resultData;
    }

    /**
     * 根据目的地, 邮费档位，托盘数量，是否超长，计算邮费
     */
    public double selectCost(String destination, int s, int i, boolean isSize) throws Exception {

        List<Cost> list;

        try {
            list = costMapper.selectDataByName(destination);
        } catch (Exception e) {
            throw new Exception("Destination information acquisition failed");
        }

        if (list.size() > 0 && s != 0 && i != 0) {
            //查看重量范围
            double cost = 0;
            while (s > 20) {
                //如果重量超过最大范围，拆分累加
                cost = cost + costMapper.selectCost(destination, 20);
                s = s - 20;
            }
            //小于20的正常部分
            cost = cost + costMapper.selectCost(destination, s);

            //加上托盘钱
            cost = cost + i * 25;
            //判断是否超长，超长加收 150
            if (!isSize) {
                cost = cost + 150;
            }

            //将结果保留两位小数
            BigDecimal bg = new BigDecimal(cost).setScale(2, RoundingMode.UP);
            cost = bg.doubleValue();
            return cost;
        } else {
            return 0.0;
        }
    }


    /**
     * 根据体积计算托盘数量
     */
    public int manageDataI(int v) {
        //根据总体积计算所需托盘的数量
        int i = v / (40 * 48 * 68);
        //如果不能整除，需要多加一个托盘
        if (v % (40 * 48 * 68) != 0) {
            i++;
        }
        //设置托盘数量
        return i;
    }


    /**
     * 根据重量计算计费档位
     */
    public int manageDataS(int w) {
        //计算z总重量所在邮费的档位
        int s = w / 100;
        //如果不能整除，加一，匹配下一档
        if (w % 100 != 0) {
            s++;
        }
        //设置计费档位
        return s;
    }
}
