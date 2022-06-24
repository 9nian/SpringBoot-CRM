package com.gb.crm.workbench.service.impl;

import com.gb.crm.workbench.domain.Contacts;
import com.gb.crm.workbench.mapper.ContactsMapper;
import com.gb.crm.workbench.service.ContactsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("contactsService")
public class ContactsServiceImpl implements ContactsService {

    @Resource
    private ContactsMapper contactsMapper;

    @Override
    public List<Contacts> queryContactsByConditionForPage(Map<String, Object> map) {
        return contactsMapper.selectContactsByConditionForPage(map);
    }

    @Override
    public int queryCountOfContactsByCondition(Map<String, Object> map) {
        return contactsMapper.selectCountOfContactsByCondition(map);
    }

    @Override
    public int saveCreateContacts(Contacts contacts) {
        return contactsMapper.insertContacts(contacts);
    }

    @Override
    public List<Contacts> queryContactsForConvertByNameCustomerId(Map<String, Object> map) {
        return contactsMapper.selectContactsForConvertByNameCustomerId(map);
    }

    @Override
    public int deleteContactsByIds(String[] id) {
        return contactsMapper.deleteContactsByIds(id);
    }

    @Override
    public Contacts queryContactsDateById(String id) {
        return contactsMapper.selectContactsDateById(id);
    }

    @Override
    public List<Contacts> queryContactsByCustomerId(String customerId) {
        return contactsMapper.selectContactsByCustomerId(customerId);
    }
}
