package com.gb.crm.setting.service;


import com.gb.crm.setting.domain.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User queryUserByLoginActAndPwd(Map<String,Object> map);

    //查询所有用户信息
    List<User> queryAllUsers();


}
