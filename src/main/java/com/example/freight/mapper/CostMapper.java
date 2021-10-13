package com.example.freight.mapper;


import com.example.freight.domain.Cost;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CostMapper {

    /**
     * 根据目的地判断，是否在数据库里有对应的数据
     * */
    List<Cost> selectDataByName(String name);

    /**
     * 根据目的地名字和重量确定价格
     * */
    double selectCost(String name,int weight);
}
