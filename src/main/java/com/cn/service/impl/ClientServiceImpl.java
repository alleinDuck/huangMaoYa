package com.cn.service.impl;

import com.cn.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl {

    @Autowired
    private UserService userService;

    public void UserInfo(){
        userService.UserInfo();
    }
}
