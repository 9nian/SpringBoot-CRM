package com.gb.crm.workbench.service.impl;

import com.gb.crm.workbench.domain.ContactsRemark;
import com.gb.crm.workbench.mapper.ContactsRemarkMapper;
import com.gb.crm.workbench.service.ContactsRemarkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("contactsRemarkService")
public class ContactsRemarkServiceImpl implements ContactsRemarkService {

    @Resource
    private ContactsRemarkMapper contactsRemarkMapper;
    @Override
    public List<ContactsRemark> queryContactsRemarkByContactsId(String contactsId) {
        return contactsRemarkMapper.selectContactsRemarkByContactsId(contactsId);
    }

    @Override
    public int saveContactsRemark(ContactsRemark remark) {
        return contactsRemarkMapper.insertContactsRemark(remark);
    }

    @Override
    public int deleteContactsRemarkById(String id) {
        return contactsRemarkMapper.deleteContactsRemarkById(id);
    }

    @Override
    public int updateContactsRemarkById(ContactsRemark remark) {
        return contactsRemarkMapper.updateContactsRemarkById(remark);
    }
}
