package com.example.freight.controller;


import com.example.freight.domain.*;
import com.example.freight.service.ICostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin
@RequestMapping("/cost")
@RequiredArgsConstructor
public class CostController {

//    @Autowired
    private final ICostService costService;

    /**
     * 根据form表单输入的信息处理数据
     *
     * @Prarm formObject表单
     * @Return 处理成功的运费信息
     */
    @RequestMapping("/selectCost")
    public ResultData selectCost(@RequestBody FormObject form) {

        ResultData resultData = new ResultData();

        try {
            //根据输入列表获取产品信息
            resultData = costService.selectParameter(form.getZipCode(), form.getDomains(), form.isType());
        } catch (Exception e) {
            resultData.setMsg(e.getMessage());
        }
        return resultData;
    }
}
