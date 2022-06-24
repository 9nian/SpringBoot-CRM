package com.gb.crm.setting.service.impl;

import com.gb.crm.setting.domain.User;
import com.gb.crm.setting.mapper.UserMapper;
import com.gb.crm.setting.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private  UserMapper userMapper;

    @Override
    public User queryUserByLoginActAndPwd(Map<String,Object> map) {

      return userMapper.selectUserByLoginActAndPwd(map);
    }

    @Override
    public List<User> queryAllUsers() {
        return userMapper.selectAllUser();
    }
}
