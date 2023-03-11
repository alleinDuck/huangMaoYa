package com.cn.service.impl;

import com.cn.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void UserInfo() {
        System.out.println(".....doing");
    }
}
