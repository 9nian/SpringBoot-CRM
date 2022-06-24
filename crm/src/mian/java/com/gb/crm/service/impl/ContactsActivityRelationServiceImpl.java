package com.gb.crm.workbench.service.impl;

import com.gb.crm.workbench.domain.ContactsActivityRelation;
import com.gb.crm.workbench.mapper.ContactsActivityRelationMapper;
import com.gb.crm.workbench.service.ContactsActivityRelationService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Service("contactsActivityRelationService")
public class ContactsActivityRelationServiceImpl implements ContactsActivityRelationService {

    @Resource
    private ContactsActivityRelationMapper contactsActivityRelationMapper;

    @Override
    public int saveContactsActivityRelationByList(List<ContactsActivityRelation> list) {
        return contactsActivityRelationMapper.insertContactsActivityRelationByList(list);
    }
}
