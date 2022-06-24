package com.gb.crm.workbench.service;


import com.gb.crm.workbench.domain.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    int saveCustomer(Customer customer);

    List<Customer> queryCustomerByConditionForPage(Map<String,Object> map);

    int queryCountOfCustomerByCondition(Map<String,Object> map);

    int deleteCustomerByIds(String [] id);

    Customer queryCustomerById(String id);

    int saveEditCustomer(Customer customer);

    Customer queryCustomerForDetailById(String id);

    Customer queryCustomerByName(String name);

    List<String> queryCustomerNameByName(String name);
}
