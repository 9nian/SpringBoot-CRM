package com.gb.crm.workbench.service;

import com.gb.crm.workbench.domain.ContactsRemark;

import java.util.List;

public interface ContactsRemarkService {

    List<ContactsRemark> queryContactsRemarkByContactsId(String contactsId);

    int saveContactsRemark(ContactsRemark remark);

    int deleteContactsRemarkById(String id);

    int updateContactsRemarkById(ContactsRemark remark);
}
