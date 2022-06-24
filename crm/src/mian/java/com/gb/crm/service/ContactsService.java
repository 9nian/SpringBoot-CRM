package com.gb.crm.workbench.service;

import com.gb.crm.workbench.domain.Contacts;

import java.util.List;
import java.util.Map;

public interface ContactsService {


    List<Contacts> queryContactsByConditionForPage(Map<String,Object> map);

    int queryCountOfContactsByCondition(Map<String,Object> map);

    int saveCreateContacts(Contacts contacts);

    List<Contacts> queryContactsForConvertByNameCustomerId(Map<String,Object> map);

    int deleteContactsByIds(String [] id);

    Contacts queryContactsDateById(String id);

    List<Contacts> queryContactsByCustomerId(String customerId);

}
