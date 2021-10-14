package com.example.freight.controller;

import com.example.freight.domain.FormObject;
import com.example.freight.domain.ResultData;
import com.example.freight.domain.Stock;
import com.example.freight.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/stock")
public class StockController {

    @Autowired
    IStockService stockService;

    /**
     * 更新库存
     */
    @RequestMapping(value = "/updateStock", method = RequestMethod.POST)
    public ResultData updateStock(@RequestBody FormObject formObject) {
//        System.out.println(stock);
        return stockService.updateStock(formObject.getDomains());
    }

    /**
     * 根据通用型号查询所有颜色及库存
     */
    @RequestMapping(value = "/selectStockList", method = RequestMethod.POST)
    public ResultData selectStockList(String sku) {
        return stockService.selectStockList(sku);
    }

    /**
     * 删除颜色
     * */
    @RequestMapping(value = "deleteStock", method = RequestMethod.POST)
    public ResultData deleteStock(String sku) {
        return stockService.deleteStock(sku);
    }
}
