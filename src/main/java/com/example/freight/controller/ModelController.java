package com.example.freight.controller;


import com.example.freight.domain.Domains;
import com.example.freight.domain.FormObject;
import com.example.freight.domain.Model;
import com.example.freight.domain.ResultData;
import com.example.freight.service.IModelService;
import com.example.freight.tool.TypeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/model")
public class ModelController {

    @Autowired
    IModelService modelService;

    /**
     * 分页查询外加搜索框模糊查询，搜索框为空时，查所有
     *
     * @param "当前页数page"，每页信息条数num，搜索的内容str
     * @return 查询到的型号列表
     */
    @RequestMapping("/modelLimit")
    public ResultData modelLimit(int page, int num, String str) {
        return modelService.modelLimit(page, num, str);
    }

    /**
     * 新增model
     *
     * @param "sku的完整信息"
     * @return 操作信息
     */
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResultData insertModel(Model model) {

        ResultData resultData = new ResultData();
        //如果信息不符合标准，直接返回
        if (TypeTool.judgmentNull(model)){
            resultData.setMsg("Please enter the correct information！");
            return resultData;
        }
        try {
           resultData = modelService.insertModel(model);
        }catch (Exception e){
            resultData.setMsg(e.getMessage());
        }
        return resultData;
    }

    /**
     * 更新model
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResultData updateModel(Model model) {

        ResultData resultData = new ResultData();
        //如果信息不符合标准，直接返回
        if (TypeTool.judgmentNull(model)){
            resultData.setMsg("Please enter the correct information！");
            return resultData;
        }
        try {
            resultData = modelService.updateModel(model);
        }catch (Exception e){
            resultData.setMsg(e.getMessage());
        }
        return resultData;
    }

    /**
     * 删除model
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResultData deleteModel(int id) {
        return modelService.deleteModel(id);
    }


}
