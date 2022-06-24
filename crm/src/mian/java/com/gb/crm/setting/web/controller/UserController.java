package com.gb.crm.setting.web.controller;


import com.gb.crm.commons.constant.Constant;
import com.gb.crm.commons.domain.ReturnObject;
import com.gb.crm.commons.utils.DateUtils;
import com.gb.crm.setting.domain.User;
import com.gb.crm.setting.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/settings/qx/user")
public class UserController {

    @Resource
    UserService userService;

    @RequestMapping("/toLogin.do")
    public String toLogin(){
        System.out.println("");
        return "/settings/qx/user/login";
    }

    @RequestMapping("/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletRequest request,
                        HttpServletResponse response){
        Map<String,Object> map = new HashMap<>();
        map.put("loginAct",loginAct);
        map.put("loginPwd",loginPwd);
        User user = userService.queryUserByLoginActAndPwd(map);
        ReturnObject returnObject = new ReturnObject();
        if (user == null){
            //用户名或者密码错误
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("用户名或者密码错误");
        }else {
            //判断账号是否合法
            String str = DateUtils.formatDateTime(new Date());
            if (str.compareTo(user.getExpireTime())>0){
                //登陆失败，账号已过期
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("账号已过期");
            }else if("0".equals(user.getLockState())){
                //登陆失败，状态被锁定
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("状态被锁定");
            }else if(!user.getAllowIps().contains(request.getRemoteAddr())){
                //登陆失败，ip地址受限
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("ip地址受限");
            }else {
                //登陆成功
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                request.getSession().setAttribute(Constant.SESSION_USER,user);
                if ("true".equals(isRemPwd)){
                    Cookie c1 = new Cookie("loginAct",loginAct);
                    c1.setMaxAge(10*24*60*60);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd",loginPwd);
                    c2.setMaxAge(10*24*60*60);
                    response.addCookie(c2);
                }else {
                    Cookie c1 = new Cookie("loginAct","1");
                    c1.setMaxAge(0);
                    response.addCookie(c1);
                    Cookie c2 = new Cookie("loginPwd","1");
                    c2.setMaxAge(0);
                    response.addCookie(c2);
                }
            }
        }

        return  returnObject;
    }


    @RequestMapping("/logout.do")
    public String logout(HttpServletResponse response, HttpSession session){

        //清除cookie
        Cookie c1 = new Cookie("loginAct","1");
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd","1");
        c2.setMaxAge(0);
        response.addCookie(c2);

        //销毁session
        session.invalidate();

        return "redirect:/";
    }
}
