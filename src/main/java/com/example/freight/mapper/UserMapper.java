package com.example.freight.mapper;

import com.example.freight.domain.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper {

    /**
     * 后台登陆验证
     * */
    User userLogin(String userName,String password);

}
