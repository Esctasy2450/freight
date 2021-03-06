package com.example.freight.service.Impl;

import com.example.freight.domain.Domains;
import com.example.freight.domain.Model;
import com.example.freight.domain.ResultData;
import com.example.freight.mapper.ModelMapper;
import com.example.freight.service.IModelService;
import com.example.freight.tool.TypeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelServiceImpl implements IModelService {

    @Autowired
    ModelMapper modelMapper;

    /**
     * 根据型号列表和是否组装检验信息
     */
    public List<Domains> selectModel(List<Domains> domains, int c) {

        Map<String, Integer> map = new HashMap<>();
        //数组去重，合并总数量
        for (Domains domain : domains) {
            //当遍历到的sku存在map.key时，将数量相加
            if (map.get(domain.getSku()) != null) {
                domain.setNum(map.get(domain.getSku()) + domain.getNum());
            }
            //sku当第一次创建map时，存数据
            map.put(domain.getSku(), domain.getNum());
        }

        //将map值取出存放到list
        List<Domains> d = new ArrayList<>();
        for (Domains domain : domains) {
            //当map.value与当前数量相同时，取出该数据
            if (map.get(domain.getSku()) == domain.getNum()) {
                d.add(domain);
            }
        }

        //将list转为map，方便匹配
        List<Model> list = modelMapper.selectStock(d);
        Map<String, Integer> m = list.stream().collect(Collectors.toMap(model -> model.getColor() + "-" + model.getSku(), Model::getStock, (k1, k2) -> k2));

        //检验型号是否存在于库中，没有输出报错信息
        for (Domains domain : d) {
            if (m.get(domain.getSku().toUpperCase()) != null) {
                domain.setStock(m.get(domain.getSku().toUpperCase()));
            } else if (0 == c) {
                domain.setSku("SKU“ " + domain.getSku() + " ”doesn't exist in the inventory list and is not included when calculating the shipping cost");
            }
        }

        return d;
    }

    /**
     * 根据型号列表和是否组装获取重量和体积
     */
    public Model getVW(List<Domains> domains, boolean type) {
        Model m = new Model();

        double allVolume = 0D;
        int allWeight = 0;
        boolean isLong = true;

        //treeMap可以忽略大小写
        TreeMap<String, Integer> map = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        for (Domains s : domains) {
            map.put(s.getNewSku(), s.getNum());
        }

        //根据型号查询所有信息
        List<Model> list = modelMapper.selectWeightAndV(domains);

        for (Model model : list) {
            if (type && model.getType() == 1) {
                //type为布尔型，组装时并且可以组装
                //统一用的allVolume存体积，计算总体积， 用单个体积和一组的个数，来确定一组体积,
                allVolume += model.getVolume() * map.get(model.getSku());
            } else {
                //否则就是默认选择不组装
                //统一用的allVolume存体积,计算总体积
                allVolume += (model.getOrdinary() / model.getGroupNum()) * map.get(model.getSku());
            }

            //判断是否超长,只需超长一次，加收150
            if (isLong) {
                isLong = TypeTool.superLong(model, type, model.getType());
            }

            //计算总重量
            allWeight += (model.getWeight() / model.getGroupNum()) * map.get(model.getSku());
        }

        m.setVolume(allVolume);
        m.setWeight(allWeight);
        m.setSize(isLong);
        return m;
    }

    /**
     * 分页查询，外加输入框模糊查询
     */
    @Override
    public ResultData modelLimit(int page, int num, String str) {

        ResultData resultData = new ResultData();
        Map<String, Object> map = new HashMap<>();
        //页数与每页数据数量的乘积决定偏移
        page = (page - 1) * num;
        //取总数，和偏移之后的数据
        List<Model> list = modelMapper.selectLimit(page, num, str);

        //当正常读取信息时，设定返回值
        resultData.setCode(list.size() > 0 ? 1 : 0);
        resultData.setMsg(list.size() > 0 ? "success" : "Operation Failed");
        map.put("count", modelMapper.selectCount(str));
        map.put("list", list);
        resultData.setData(map);

        return resultData;
    }

    /**
     * 根据id删除型号
     */
    @Override
    public ResultData deleteModel(int id) {

        int i = modelMapper.deleteModel(id);
        //删除成功
        ResultData resultData = new ResultData();
        resultData.setCode(i);
        resultData.setMsg(i == 1 ? "success" : "Operation Failed");

        return resultData;
    }

    /**
     * 新增model
     */
    @Override
    public ResultData insertModel(Model model) throws Exception {

        ResultData resultData = new ResultData();

        try {
            //判断新增型号是否已经存在，存在返回错误信息
            if (modelMapper.selectModel(model.getSku()) != null) {
                resultData.setMsg("This SKU already exists！");
                return resultData;
            }

            //数据库返回多条信息时会报错，抛出异常
        } catch (Exception e) {
            throw new Exception("More than one SKU named:”" + model.getSku() + "“，Please contact the administrator!");
        }

        //一切正常则执行操作，新增SKU
        model.setVolume(model.getVLength() * model.getVWidth() * model.getVHeight());
        model.setOrdinary(model.getLength() * model.getWidth() * model.getHeight());
        int i = modelMapper.insertModel(model);
        resultData.setCode(i);
        resultData.setMsg(i == 1 ? "success" : "Operation Failed");

        return resultData;
    }

    /**
     * 更新model
     */
    @Override
    public ResultData updateModel(Model model) throws Exception {

        ResultData resultData = new ResultData();
        try {
            //判断修改的新型号是否已经存在，已存在则返回错误信息
            Model m = modelMapper.selectModel(model.getSku());
            if (m.getSku() != null && m.getId() != model.getId()) {
                resultData.setMsg("This SKU already exists");
                return resultData;
            }
            //数据库返回多条信息时会报错，抛出异常
        } catch (Exception e) {
            throw new Exception("More than one SKU named already exists:“" + model.getSku() + "”");
        }

        //一切正常则执行操作，修改SKU
        model.setVolume(model.getVLength() * model.getVWidth() * model.getVHeight());
        model.setOrdinary(model.getLength() * model.getWidth() * model.getHeight());
        int i = modelMapper.updateModel(model);
        resultData.setCode(i);
        resultData.setMsg(i == 1 ? "success" : "Operation Failed");

        return resultData;
    }

}
