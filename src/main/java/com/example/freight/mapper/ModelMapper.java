package com.example.freight.mapper;

import com.example.freight.domain.Domains;
import com.example.freight.domain.Model;
import com.example.freight.domain.Stock;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ModelMapper {

      /**
       * 查询家具参数
       *
       * @param sku*/
      List<Model> selectWeightAndV(List<Domains> sku);

      /**
       * 验证型号是否存在
       * */
      Model selectModel(String sku);

      /**
       * 查询总数
       * */
      int selectCount(String str);

      /**
       * 分页查询，外加输入框模糊查询
       * */
      List<Model> selectLimit(int start, int num, String str);

      /**
       * 根据id删除型号
       * */
      int deleteModel(int id);

      /**
       * 根据目的地名字和重量确定价格
       * */
      int insertModel(Model model);

      /**
       * 根据目的地名字和重量确定价格
       * */
      int updateModel(Model model);

      /**
       * 查询库存和验证型号信息
       * */
      List<Model> selectStock(List<Domains> list);

}
