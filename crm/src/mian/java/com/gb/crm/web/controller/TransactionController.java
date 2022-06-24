package com.gb.crm.workbench.web.controller;


import com.gb.crm.commons.constant.Constant;
import com.gb.crm.commons.domain.ReturnObject;
import com.gb.crm.commons.utils.DateUtils;
import com.gb.crm.commons.utils.UUIDUtils;
import com.gb.crm.setting.domain.DicValue;
import com.gb.crm.setting.domain.User;
import com.gb.crm.setting.service.DicValueService;
import com.gb.crm.setting.service.UserService;
import com.gb.crm.workbench.domain.*;
import com.gb.crm.workbench.service.*;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/workbench/transaction")
public class TransactionController {

    @Resource
    private UserService userService;

    @Resource
    private DicValueService dicValueService;

    @Resource
    private TranService tranService;

    @Resource
    private ActivityService activityService;

    @Resource
    private ContactsService contactsService;

    @Resource
    private CustomerService customerService;

    @Resource
    private TranRemarkService tranRemarkService;

    @Resource
    private TranHistoryService tranHistoryService;

    @Resource
    Environment environment;

    @RequestMapping("/index.do")
    public String index(HttpServletRequest request){
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList=dicValueService.queryDicValueByTypeCode("transactionType");

        request.setAttribute("stageList",stageList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        return "workbench/transaction/index";
    }


    @ResponseBody
    @RequestMapping("/queryTransactionByConditionForPage.do")
    public Object queryTransactionByConditionForPage(String name, String customerId, String source,
                                                     String owner, String stage, String type,
                                                     String contactsId, int pageNo, int pageSize){
        HashMap<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("customerId",customerId);
        map.put("source",source);
        map.put("owner",owner);
        map.put("stage",stage);
        map.put("type",type);
        map.put("contactsId",contactsId);
        map.put("pageNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Tran> tranList = tranService.queryTransactionByConditionForPage(map);
        int totalRows = tranService.queryCountOfTransactionByCondition(map);
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("tranList",tranList);
        retMap.put("totalRows",totalRows);
        return  retMap;
    }

    @RequestMapping("/toSave.do")
    public String toSave(HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList=dicValueService.queryDicValueByTypeCode("transactionType");
        request.setAttribute("stageList",stageList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("userList",userList);
        return "workbench/transaction/save";
    }


    @RequestMapping("/queryActivityForConvertByName.do")
    @ResponseBody
    public Object queryActivityForConvertByName(String activityName ){
        List<Activity> activityList = activityService.queryActivityForDetailByName(activityName);
        return activityList;
    }

    @RequestMapping("/queryContactsForConvertByNameCustomerId.do")
    @ResponseBody
    public Object queryContactsForConvertByNameCustomerId(String contactsName,String customerName){
        HashMap<String,Object> map = new HashMap<>();
        map.put("fullname",contactsName);
        map.put("customerName",customerName);
        List<Contacts> contactsList = contactsService.queryContactsForConvertByNameCustomerId(map);
        return contactsList;
    }

    @RequestMapping("/getPossibilityByStage.do")
    @ResponseBody
    public Object getPossibilityByStage(String stageValue){

       //读取yml配置文件，根据阶段获取可能性
        String possibility=environment.getProperty(stageValue);
        //返回响应信息
        return possibility;
    }

    @RequestMapping("/saveCreateTran.do")
    @ResponseBody
    public Object saveCreateTran(Tran tran, HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        tran.setId(UUIDUtils.getUUID());
        tran.setCreateBy(user.getId());
        tran.setCreateTime(DateUtils.formatDateTime(new Date()));

        String customerId = tran.getCustomerId();
        Customer customer  = customerService.queryCustomerByName(customerId);
        if (customer != null){
            tran.setCustomerId(customer.getId());
        }else {
            Customer newCustomer = new Customer();
            newCustomer.setCreateBy(user.getId());
            newCustomer.setCreateTime(DateUtils.formatDateTime(new Date()));
            newCustomer.setId(UUIDUtils.getUUID());
            newCustomer.setOwner(tran.getOwner());
            try {
                int result = customerService.saveCustomer(newCustomer);

                if (result == 1) {
                    tran.setCustomerId(newCustomer.getId());
                } else {
                    returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                    returnObject.setMessage("系统忙，请稍后重试。。。。。");
                    return returnObject;
                }
            }catch (Exception e){
                e.printStackTrace();
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试。。。。。");
                return returnObject;
            }
        }
        try {
            int result = tranService.saveCreateTran(tran);
            if (result == 1) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试。。。。");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试。。。。");
        }
        return  returnObject;
    }


    @RequestMapping("/toUpdate.do")
    public String toUpdate(String id,HttpServletRequest request){
        List<User> userList = userService.queryAllUsers();
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList=dicValueService.queryDicValueByTypeCode("transactionType");
        request.setAttribute("stageList",stageList);
        request.setAttribute("sourceList",sourceList);
        request.setAttribute("transactionTypeList",transactionTypeList);
        request.setAttribute("userList",userList);
        Tran tran = tranService.queryTransactionById(id);
        request.setAttribute("tran",tran);
        String possibility = "";
        for (DicValue dicValue:stageList) {
            if (dicValue.getId().equals(tran.getStage())){
                possibility=environment.getProperty(dicValue.getValue());
                break;
            }
        }
        request.setAttribute("possibility",possibility);
        return "workbench/transaction/edit";
    }

    @RequestMapping("/saveEditTran.do")
    @ResponseBody
    public Object saveEditTran(Tran tran,HttpSession session){
        User user  = (User) session.getAttribute(Constant.SESSION_USER);
        tran.setEditBy(user.getId());
        tran.setEditTime(DateUtils.formatDateTime(new Date()));
        String customerId = tran.getCustomerId();
        Customer customer  = customerService.queryCustomerByName(customerId);
        tran.setCustomerId(customer.getId());

        ReturnObject returnObject = new ReturnObject();
        try {
            int result = tranService.updateTransactionByTranId(tran);
            if (result == 1) {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            } else {
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

    @RequestMapping("/deleteTransactionByIds.do")
    @ResponseBody
    public Object deleteTransactionByIds(String [] id){
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = tranService.deleteTransactionByIds(id);
            if (result > 0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试。。。。。");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试。。。。。");
        }
        return  returnObject;
    }

    @RequestMapping("/detailTran.do")
    public String detailTran(String id,HttpServletRequest request){
        //调用service层方法，查询数据
        Tran tran=tranService.queryTranForDetailById(id);
        List<TranRemark> remarkList=tranRemarkService.queryTranRemarkForDetailByTranId(id);
        List<TranHistory> historyList=tranHistoryService.queryTranHistoryForDetailByTranId(id);

        //根据tran所处阶段名称查询可能性
        String possibility=environment.getProperty(tran.getStage());
        tran.setPossibility(possibility);

        //把数据保存到request中

        //request.setAttribute("possibility",possibility);

        //调用service方法，查询交易所有的阶段
        List<DicValue> stageList=dicValueService.queryDicValueByTypeCode("stage");
        request.setAttribute("stageList",stageList);
        for (DicValue d:stageList) {
            if (d.getValue().equals(tran.getStage())){
                tran.setOrderNo(d.getOrderNo());
                break;
            }
        }
        request.setAttribute("tran",tran);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("historyList",historyList);
        //请求转发
        return "workbench/transaction/detail";
    }

}
