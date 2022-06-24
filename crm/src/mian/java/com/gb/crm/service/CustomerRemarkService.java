package com.gb.crm.workbench.service;

import com.gb.crm.workbench.domain.ClueRemark;
import com.gb.crm.workbench.domain.CustomerRemark;

import java.util.List;

public interface CustomerRemarkService {

    List<CustomerRemark> queryCustomerRemarkForDetailByCustomerId(String id);

    int saveCreateCustomerRemark(CustomerRemark remark);

    int deleteCustomerRemarkById(String id);

    int updateCustomerRemarkById(CustomerRemark remark);
}
