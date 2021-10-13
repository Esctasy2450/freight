package com.example.freight.service;

import com.example.freight.domain.Domains;
import com.example.freight.domain.ResultData;
import com.example.freight.domain.Stock;

import java.util.List;

public interface IStockService {

    /**
     * 更新库存
     * */
    ResultData updateStock(List<Domains> domains);

    /**
     * 根据通用型号查询所有颜色及库存
     * */
    ResultData selectStockList(String sku);

    /**
     * 删除颜色
     * */
    ResultData deleteStock(String sku);
}
