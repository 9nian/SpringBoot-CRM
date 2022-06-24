package com.gb.crm.workbench.web.controller;

import com.gb.crm.commons.constant.Constant;
import com.gb.crm.commons.domain.ReturnObject;
import com.gb.crm.commons.utils.DateUtils;
import com.gb.crm.commons.utils.UUIDUtils;
import com.gb.crm.setting.domain.User;
import com.gb.crm.workbench.domain.ActivityRemark;
import com.gb.crm.workbench.service.ActivityRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

@RequestMapping("/workbench/activity")
@Controller
public class ActivityRemarkController {


    @Autowired
    private ActivityRemarkService activityRemarkService;

    @RequestMapping("/saveCreateActivityRemark")
    @ResponseBody
    public Object saveCreateActivityRemark(ActivityRemark remark, HttpSession session){
       User user = (User) session.getAttribute(Constant.SESSION_USER);
       remark.setId(UUIDUtils.getUUID());
       remark.setCreateBy(user.getId());
       remark.setCreateTime(DateUtils.formatDateTime(new Date()));
       remark.setEditFlag(Constant.REMARK_EDIT_FLAG_NO_EDITED);
       ReturnObject returnObject = new ReturnObject();
       try {
           int result = activityRemarkService.saveCreateActivityRemark(remark);
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

    @RequestMapping("/deleteActivityRemarkById.do")
    @ResponseBody
    public Object deleteActivityRemarkById(String id) {
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = activityRemarkService.deleteActivityRemarkById(id);
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

    @RequestMapping("/saveEditActivityRemarkById.do")
    @ResponseBody
    public Object saveEditActivityRemarkById(ActivityRemark remark,HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        //封装参数
        remark.setEditFlag(Constant.REMARK_EDIT_FLAG_YES_EDITED);
        remark.setEditBy(user.getId());
        remark.setEditTime(DateUtils.formatDateTime(new Date()));
        //调用service层执行修改保存代码
        try {
            int result = activityRemarkService.saveEditActivityRemarkById(remark);
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
