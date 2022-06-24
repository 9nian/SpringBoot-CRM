package com.gb.crm.workbench.service;

import com.gb.crm.workbench.domain.ContactsActivityRelation;

import java.util.List;

public interface ContactsActivityRelationService {

   int saveContactsActivityRelationByList(List<ContactsActivityRelation> list);
}
