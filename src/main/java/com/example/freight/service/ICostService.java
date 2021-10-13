package com.example.freight.service;

import com.example.freight.domain.Domains;
import com.example.freight.domain.ResultData;

import java.util.List;

public interface ICostService {


    /**
     * 查询家具参数并处理
     * */
    ResultData selectParameter(String destination, List<Domains> domains, boolean type) throws Exception;


}
