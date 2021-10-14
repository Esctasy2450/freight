package com.example.freight.service.Impl;

import com.example.freight.domain.Code;
import com.example.freight.mapper.CodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CodeServiceImpl {

    @Autowired
    CodeMapper codeMapper;

    /**
     * 根据邮编确定目的地
     */
    public Code selectNameByZipCode(String zipCode) throws Exception {

        Code code = new Code();

        String name;
        try {
            // 取邮编前三位并转化为Integer型,根据结果验证目的地是否存在
            name = codeMapper.selectNameByZipCode(Integer.parseInt(zipCode.substring(0, 3)));
        } catch (Exception e) {
            throw new Exception("Wrong zip code type: " + zipCode);
        }
        //查询目的地是否为空
        code.setNull(StringUtils.isEmpty(name));
        code.setDestination(StringUtils.isEmpty(name) ? "Destination not found" : name);
        return code;
    }
}
