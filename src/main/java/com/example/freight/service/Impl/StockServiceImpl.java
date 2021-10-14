package com.example.freight.service.Impl;

import com.example.freight.domain.Domains;
import com.example.freight.domain.ResultData;
import com.example.freight.domain.Stock;
import com.example.freight.mapper.ModelMapper;
import com.example.freight.mapper.StockMapper;
import com.example.freight.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements IStockService {

    @Autowired
    StockMapper stockMapper;

    @Autowired
    ModelMapper modelMapper;

    /**
     * 修改库存
     */
    @Override
    public ResultData updateStock(List<Domains> domains) {

        //遍历数组，取数据
        for (Domains domain : domains) {
            //根据SKU和颜色修改库存，重名不影响操作，
            if (stockMapper.selectStock(domain.getNewSku(), domain.getColor()) != null) {
                //如果查到原有记录则更新
                stockMapper.updateStock(domain.getNewSku(), domain.getColor(), domain.getNewStock());
            } else {
                //否则为新增记录
                stockMapper.insertStock(domain.getNewSku(), domain.getColor(), domain.getNewStock());
            }
        }

        ResultData resultData = new ResultData();
        resultData.setCode(1);
        resultData.setMsg("success");

        return resultData;
    }

    /**
     * 根据通用型号查询所有颜色的库存
     */
    @Override
    public ResultData selectStockList(String sku) {

        //获取当前型号所有颜色及库存
        List<Stock> list = stockMapper.selectStockList(sku);

        for (Stock stock : list) {
            //将颜色和型号拼接在一起
            stock.setSku(stock.getColor() + "-" + stock.getSku());
            //将新旧库存先保持一致，防止修改库存时被置为空
            stock.setNewStock(stock.getStock());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("list", list);

        ResultData resultData = new ResultData();

        resultData.setCode(1);
        resultData.setData(map);
        resultData.setMsg("success");

        return resultData;
    }

    /**
     * 删除库存颜色
     */
    @Override
    public ResultData deleteStock(String sku) {

        //获取去除颜色的型号
        String s = sku.substring(sku.indexOf("-") + 1);
        //获取颜色
        String color = sku.substring(0, sku.indexOf("-"));

        //当此颜色的库存存在时，删除颜色
        if (stockMapper.selectStock(s, color) != null) {
            //删除颜色
            stockMapper.deleteStock(s, color);
        }

        ResultData resultData = new ResultData();
        resultData.setCode(1);
        resultData.setMsg("success");
        return resultData;
    }
}
