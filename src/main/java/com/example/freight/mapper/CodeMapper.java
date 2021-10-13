package com.example.freight.mapper;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CodeMapper {


    /**
     * 根据邮编的前三位查询属于哪个地区
     * */
    String selectNameByZipCode(int zipCode);

}
