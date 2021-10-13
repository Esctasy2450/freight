package com.example.freight.mapper;

import com.example.freight.domain.Stock;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StockMapper {

    /**
     * 检验库存是否存在
     * */
    Stock selectStock(String sku, String color);

    /**
     * 根据型号和颜色 更新 库存
     * */
    void updateStock(String sku, String color, int stock);

    /**
     * 根据型号和颜色 新增 库存
     * */
    void insertStock(String sku, String color, int stock);

    /**
     * 根据通用型号查询所有颜色及库存
     * */
    List<Stock> selectStockList(String sku);

    /**
     * 删除颜色
     * */
    int deleteStock(String sku, String color);

}
