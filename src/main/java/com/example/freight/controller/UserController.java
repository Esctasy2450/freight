package com.example.freight.controller;


import com.example.freight.domain.ResultData;
import com.example.freight.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    /**
     * 后台登陆验证
     * */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResultData userLogin(String userName,String password){
        return userService.userLogin(userName, password);
    }

}
