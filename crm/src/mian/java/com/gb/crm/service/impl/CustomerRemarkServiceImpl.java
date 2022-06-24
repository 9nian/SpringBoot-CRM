package com.gb.crm.workbench.service.impl;

import com.gb.crm.workbench.domain.CustomerRemark;
import com.gb.crm.workbench.mapper.CustomerRemarkMapper;
import com.gb.crm.workbench.service.CustomerRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("customerRemarkService")
public class CustomerRemarkServiceImpl implements CustomerRemarkService {

    @Resource
    private CustomerRemarkMapper customerRemarkMapper;
    @Override
    public List<CustomerRemark> queryCustomerRemarkForDetailByCustomerId(String id) {
        return customerRemarkMapper.selectCustomerRemarkForDetailByCustomerId(id);
    }

    @Override
    public int saveCreateCustomerRemark(CustomerRemark remark) {
        return customerRemarkMapper.insertCreateCustomerRemark(remark);
    }

    @Override
    public int deleteCustomerRemarkById(String id) {
        return customerRemarkMapper.deleteCustomerRemarkById(id);
    }

    @Override
    public int updateCustomerRemarkById(CustomerRemark remark) {
        return customerRemarkMapper.updateCustomerRemarkById(remark);
    }
}
