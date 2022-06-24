package com.gb.crm.workbench.web.controller;

import com.gb.crm.commons.constant.Constant;
import com.gb.crm.commons.domain.ReturnObject;
import com.gb.crm.commons.utils.DateUtils;
import com.gb.crm.commons.utils.UUIDUtils;
import com.gb.crm.setting.domain.User;
import com.gb.crm.setting.service.UserService;
import com.gb.crm.workbench.domain.Contacts;
import com.gb.crm.workbench.domain.Customer;
import com.gb.crm.workbench.domain.CustomerRemark;
import com.gb.crm.workbench.domain.Tran;
import com.gb.crm.workbench.service.ContactsService;
import com.gb.crm.workbench.service.CustomerRemarkService;
import com.gb.crm.workbench.service.CustomerService;
import com.gb.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/workbench/customer")
@Controller
public class CustomerController {

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRemarkService customerRemarkService;

    @Resource
    private TranService tranService;

    @Resource
    private ContactsService contactsService;

    @RequestMapping("/index.do")
    public String index(HttpServletRequest request){
        List<User> userList =  userService.queryAllUsers();
        request.setAttribute("userList",userList);
        return "workbench/customer/index";
    }

    @RequestMapping("/saveCustomer.do")
    @ResponseBody
    public Object saveCustomer(Customer customer, HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        customer.setCreateBy(user.getId());
        customer.setCreateTime(DateUtils.formatDateTime(new Date()));
        customer.setId(UUIDUtils.getUUID());
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = customerService.saveCustomer(customer);
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



    @RequestMapping("/queryCustomerByConditionForPage.do")
    @ResponseBody
    public Object queryCustomerByConditionForPage(String name,String phone, String owner,
                                                  String website, int pageNo,int pageSize){
        Map<String,Object> map = new HashMap<>();
        map.put("name",name);
        map.put("phone",phone);
        map.put("owner",owner);
        map.put("website",website);
        map.put("pageNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        List<Customer> customerList = customerService.queryCustomerByConditionForPage(map);
        int totalRows = customerService.queryCountOfCustomerByCondition(map);
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("customerList",customerList);
        retMap.put("totalRows",totalRows);
        return  retMap;
    }


    @RequestMapping("/deleteCustomerByIds.do")
    @ResponseBody
    public Object deleteCustomerByIds(String [] id){
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = customerService.deleteCustomerByIds(id);
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

    @RequestMapping("/queryCustomerById.do")
    @ResponseBody
    public Object queryCustomerById(String id){
      Customer customer  = customerService.queryCustomerById(id);
        return customer;
    }

    @RequestMapping("/saveEditCustomer.do")
    @ResponseBody
    public Object saveEditCustomer(Customer customer,HttpSession session){
        User user = (User) session.getAttribute(Constant.SESSION_USER);
        customer.setEditBy(user.getId());
        customer.setEditTime(DateUtils.formatDateTime(new Date()));
        ReturnObject returnObject = new ReturnObject();
        try {
            int result = customerService.saveEditCustomer(customer);
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

    @RequestMapping("/detailCustomer.do")
    public String detailCustomer(String id,HttpServletRequest request){
        Customer customer = customerService.queryCustomerForDetailById(id);
        List<CustomerRemark> remarkList = customerRemarkService.queryCustomerRemarkForDetailByCustomerId(id);
        List<Tran> tranList = tranService.queryTransactionByCustomerId(id);
        List<Contacts> contactsList = contactsService.queryContactsByCustomerId(id);
        request.setAttribute("customer",customer);
        request.setAttribute("remarkList",remarkList);
        request.setAttribute("tranList",tranList);
        request.setAttribute("contactsList",contactsList);
        return "workbench/customer/detail";
    }

    @RequestMapping("/queryCustomerNameByName.do")
    @ResponseBody
    public Object queryCustomerNameByName(String name){
       List<String> nameList = customerService.queryCustomerNameByName(name);
       return nameList;
    }


}
