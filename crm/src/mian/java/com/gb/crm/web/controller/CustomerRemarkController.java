package com.gb.crm.workbench.web.controller;


import com.gb.crm.commons.constant.Constant;
import com.gb.crm.commons.domain.ReturnObject;
import com.gb.crm.commons.utils.DateUtils;
import com.gb.crm.commons.utils.UUIDUtils;
import com.gb.crm.setting.domain.User;
import com.gb.crm.workbench.domain.CustomerRemark;
import com.gb.crm.workbench.service.CustomerRemarkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/workbench/CustomerRemark")
public class CustomerRemarkController {

    @Resource
    private CustomerRemarkService customerRemarkService;

    @RequestMapping("/saveCreateCustomerRemark.do")
    @ResponseBody
    public Object saveCreateCustomerRemark(CustomerRemark remark, HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        remark.setCreateBy(user.getId());
        remark.setCreateTime(DateUtils.formatDateTime(new Date()));
        remark.setId(UUIDUtils.getUUID());
        ReturnObject returnObject = new ReturnObject();
        try{
            int result = customerRemarkService.saveCreateCustomerRemark(remark);
            if (result == 1){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(remark);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试。。。");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试。。。");
        }
        return  returnObject;
    }


    @RequestMapping("/deleteCustomerRemarkById.do")
    @ResponseBody
    public Object deleteCustomerRemarkById(String id){
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = customerRemarkService.deleteCustomerRemarkById(id);
            if (result == 1){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试。。。。");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试。。。。");
        }
        return returnObject;
    }

    @RequestMapping("/saveEditCustomerRemarkById.do")
    @ResponseBody
    public  Object  saveEditCustomerRemarkById(CustomerRemark remark,HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        //封装参数
        remark.setEditFlag(Constant.REMARK_EDIT_FLAG_YES_EDITED);
        remark.setEditBy(user.getId());
        remark.setEditTime(DateUtils.formatDateTime(new Date()));
        //调用service层执行修改保存代码
        try {
            int result = customerRemarkService.updateCustomerRemarkById(remark);
            if(result == 1){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
                returnObject.setRetData(remark);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试。。。。");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试。。。。");
        }
        return returnObject;
    }

}
