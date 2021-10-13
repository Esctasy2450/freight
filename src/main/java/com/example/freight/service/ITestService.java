package com.example.freight.service;

import com.example.freight.domain.FormObject;
import com.example.freight.domain.ResultData;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ITestService {

    /**
     * 根据型号数量获取运费
     * */
    ResultData insertChoice(InputStream excelFile, String fileName) throws Exception;

    /**
     * 下载当前数据文件
     * */
    void downland(FormObject formObject, HttpServletResponse response);

    /**
     * 根据excel更新运费信息
     * */
    ResultData updateStock(InputStream excelFile, String fileName) throws Exception;

    /**
     * 根据长字符串下载excel表格
     * */
    void getText(String str,HttpServletResponse response);

}
