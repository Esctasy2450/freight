package com.example.freight.service;

import com.example.freight.domain.Domains;
import com.example.freight.domain.Model;
import com.example.freight.domain.ResultData;

import java.util.List;

public interface IModelService {


    /**
     * 分页查询，外加输入框模糊查询
     * */
    ResultData modelLimit(int page,int num,String str);

    /**
     * 根据id删除型号
     * */
    ResultData deleteModel(int id);

    /**
     * 新增model
     * */
    ResultData insertModel(Model model) throws Exception;

    /**
     * 更新model
     * */
    ResultData updateModel(Model model) throws Exception;


}
