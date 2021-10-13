package com.example.freight.service.Impl;

import com.example.freight.domain.Domains;
import com.example.freight.domain.Model;
import com.example.freight.domain.ResultData;
import com.example.freight.mapper.ModelMapper;
import com.example.freight.service.IModelService;
import com.example.freight.tool.TypeTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ModelServiceImpl implements IModelService {

    @Autowired
    ModelMapper modelMapper;

    /**
     * 根据型号列表和是否组装检验信息
     */
    public void selectModel(List<Domains> domains) throws Exception {

        //先检查型号信息是否正确
        for (Domains domain : domains) {
            Model model;
            try {
                    model = modelMapper.selectStock(domain.getNewSku(), domain.getColor());
            } catch (Exception e) {
                throw new Exception("More than one SKU named:”" + domain.getSku() + "“，Please contact the administrator!");
            }

            if (model == null) {
                //如果根据型号查询信息为空，直接将原sku赋值为报错信息
                domain.setSku("SKU“ " + domain.getNewSku() + " ”doesn't exist in the inventory list and is not included when calculating the shipping cost");
            } else {
                //型号正确的话将库存添加进数组
                domain.setStock(model.getStock());
            }
        }
    }

    /**
     * 根据型号列表和是否组装获取重量和体积
     */
    public Model getVW(List<Domains> domains, boolean type) {
        Model m = new Model();

        double allVolume = 0D;
        int allWeight = 0;
        boolean isLong = true;
        for (Domains s : domains) {
            Model model;
            //根据型号查询所有信息
            model = modelMapper.selectWeightAndV(s.getNewSku());

            if (model != null) {
                if (type && model.getType() == 1) {
                    //type为布尔型，组装时并且可以组装
                    //统一用的allVolume存体积，计算总体积， 用单个体积和一组的个数，来确定一组体积,
                    allVolume += model.getVolume() * model.getGroupNum() * s.getNum();

                    //判断是否超长,只需超长一次，加收150
                    if (isLong) {
                        isLong = TypeTool.superLong((int) model.getvWidth(), (int) model.getvHeight(), (int) model.getvLength());
                    }
                } else {
                    //否则就是默认选择不组装
                    //统一用的allVolume存体积,计算总体积
                    allVolume += model.getOrdinary() * s.getNum();

                    //判断是否超长,只需超长一次，加收150
                    if (isLong) {
                        isLong = TypeTool.superLong(model.getWidth(), model.getLength(), model.getHeight());
                    }
                }
                //计算总重量
                allWeight += model.getWeight() * s.getNum();
            }
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
        model.setVolume(model.getvLength() * model.getvWidth() * model.getvHeight());
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
        model.setVolume(model.getvLength() * model.getvWidth() * model.getvHeight());
        model.setOrdinary(model.getLength() * model.getWidth() * model.getHeight());
        int i = modelMapper.updateModel(model);
        resultData.setCode(i);
        resultData.setMsg(i == 1 ? "success" : "Operation Failed");

        return resultData;
    }

}
