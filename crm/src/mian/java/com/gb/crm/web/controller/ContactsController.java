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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/workbench/contacts")
public class ContactsController {


    @Resource
    private UserService userService;

    @Resource
    private ContactsService contactsService;

    @Resource
    private DicValueService dicValueService;

    @Resource
    private CustomerService customerService;

    @Resource
    private ContactsRemarkService contactsRemarkService;

    @Resource
    private TranService tranService;
    @Resource
    private ActivityService activityService;

    @Resource
    private ContactsActivityRelationService contactsActivityRelationService;


    @RequestMapping("/index.do")
    public String index(HttpServletRequest request){
        List<User> userList =  userService.queryAllUsers();
        List<DicValue> appellationList=dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> sourceList=dicValueService.queryDicValueByTypeCode("source");
        request.setAttribute("userList",userList);
        request.setAttribute("appellationList",appellationList);
        request.setAttribute("sourceList",sourceList);
        return "workbench/contacts/index";
    }


    @RequestMapping("/queryContactsByConditionForPage.do")
    @ResponseBody
    public Object queryContactsByConditionForPage(String fullname,String customerId,String source,
                                              String owner,int pageNo,int pageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("fullname",fullname);
        map.put("source",source);
        map.put("owner",owner);
        map.put("customerId",customerId);
        map.put("pageNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Contacts> contactsList = contactsService.queryContactsByConditionForPage(map);
        int totalRows = contactsService.queryCountOfContactsByCondition(map);
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("contactsList",contactsList);
        retMap.put("totalRows",totalRows);
        return  retMap;
    }


    @RequestMapping("/saveCreateContacts.do")
    @ResponseBody
    public  Object saveCreateContacts(Contacts contacts, HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        contacts.setCreateBy(user.getId());
        contacts.setCreateTime(DateUtils.formatDateTime(new Date()));
        contacts.setId(UUIDUtils.getUUID());

        String customerId = contacts.getCustomerId();
        Customer customer  = customerService.queryCustomerByName(customerId);

        if (customer != null){
            contacts.setCustomerId(customer.getId());
        }else {
            Customer newCustomer = new Customer();
            newCustomer.setCreateBy(user.getId());
            newCustomer.setCreateTime(DateUtils.formatDateTime(new Date()));
            newCustomer.setId(UUIDUtils.getUUID());
            newCustomer.setOwner(contacts.getOwner());
            int result = customerService.saveCustomer(newCustomer);
            if (result == 1){
                contacts.setCustomerId(newCustomer.getId());
            }else {
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试。。。。。");
                return returnObject;
            }
        }
        int result = contactsService.saveCreateContacts(contacts);
        if (result == 1){
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试。。。。");
        }
        return  returnObject;
    }

    @RequestMapping("/deleteContactsByIds.do")
    @ResponseBody
    public Object deleteContactsByIds(String [] id){
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = contactsService.deleteContactsByIds(id);
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

    @RequestMapping("/detailContacts.do")
    public String detailContacts(String id,HttpServletRequest request){
        Contacts contacts = contactsService.queryContactsDateById(id);
        List<ContactsRemark> remarkList = contactsRemarkService.queryContactsRemarkByContactsId(id);
        List<Tran> tranList = tranService.queryTransactionByContactsId(id);
        List<Activity> activityList = activityService.queryActivityByContactsId(id);
        request.setAttribute("contacts",contacts);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("tranList",tranList);
        request.setAttribute("activityList",activityList);
        return "workbench/contacts/detail";
    }

    @RequestMapping("/queryActivityForDetailByNameContactsId.do")
    @ResponseBody
    public Object queryActivityForDetailByNameContactsId(String activityName,String contactsId){
        //封装参数
        Map<String,Object> map=new HashMap<>();
        map.put("activityName",activityName);
        map.put("contactsId",contactsId);
        //调用service层方法，查询市场活动
        List<Activity> activityList=activityService.queryActivityForConvertByNameContactsId(map);
        //根据查询结果，返回响应信息
        return activityList;
    }

    @RequestMapping("/saveBund.do")
    @ResponseBody
    public Object saveBund(String[] activityId,String contactsId){
        //封装参数
        ContactsActivityRelation car=null;
        List<ContactsActivityRelation> relationList=new ArrayList<>();
        for(String ai:activityId){
            car=new ContactsActivityRelation();
            car.setActivityId(ai);
            car.setContactsId(contactsId);
            car.setId(UUIDUtils.getUUID());
            relationList.add(car);
        }
        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service方法，批量保存线索和市场活动的关联关系
            int ret = contactsActivityRelationService.saveContactsActivityRelationByList(relationList);
            if(ret>0){
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_SUCCESS);

                List<Activity> activityList=activityService.queryActivityForDetailByIds(activityId);
                returnObject.setRetData(activityList);
            }else{
                returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Constant.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }

}
