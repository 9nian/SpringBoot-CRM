package com.gb.crm.workbench.web.controller;

import com.gb.crm.commons.constant.Constant;
import com.gb.crm.commons.domain.ReturnObject;
import com.gb.crm.commons.utils.DateUtils;
import com.gb.crm.setting.domain.User;
import com.gb.crm.workbench.domain.TranRemark;
import com.gb.crm.workbench.service.TranRemarkService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/workbench/transactionRemark")
public class TransactionRemarkController {

    @Resource
    private TranRemarkService tranRemarkService;

    @RequestMapping("/saveEditTranRemarkById.do")
    @ResponseBody
    public Object saveEditTranRemarkById(TranRemark remark, HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        ReturnObject returnObject = new ReturnObject();
        //封装参数
        remark.setEditFlag(Constant.REMARK_EDIT_FLAG_YES_EDITED);
        remark.setEditBy(user.getId());
        remark.setEditTime(DateUtils.formatDateTime(new Date()));
        //调用service层执行修改保存代码
        try {
            int result = tranRemarkService.updateTranRemarkById(remark);
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
