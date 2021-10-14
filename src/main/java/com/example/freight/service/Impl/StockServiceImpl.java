package com.example.freight.service.Impl;

import com.example.freight.domain.Domains;
import com.example.freight.domain.ResultData;
import com.example.freight.domain.Stock;
import com.example.freight.mapper.ModelMapper;
import com.example.freight.mapper.StockMapper;
import com.example.freight.service.IStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

        TreeMap<String, Object> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        //先验证数组的值，查询对应的库存，并将查询到的值存到map中
        List<Stock> list = stockMapper.verifyStock(domains);
        for (Stock s : list) {
            map.put(s.getColor() + "-" + s.getSku(), s.getStock());
        }

        List<Domains> insertStock = new ArrayList<>();
        List<Domains> updateStock = new ArrayList<>();

        //遍历数组domains，校验当前的sku对应的map，存在就插入更新队列，否则加入新增队列
        for (Domains d : domains) {
            if (map.get(d.getSku()) == null) {
                insertStock.add(d);
            } else {
                updateStock.add(d);
            }
        }

        //当新增队列不为空时，执行批量新增
        if (insertStock.size() > 0) {
            stockMapper.insertStock(insertStock);
        }

        //当更新队列不为空时，执行批量更新
        if (updateStock.size() > 0) {
            stockMapper.updateStock(updateStock);
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
