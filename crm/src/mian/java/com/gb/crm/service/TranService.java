package com.gb.crm.workbench.service;

import com.gb.crm.workbench.domain.FunnelVO;
import com.gb.crm.workbench.domain.Tran;


import java.util.List;
import java.util.Map;

public interface TranService {


    List<Tran> queryTransactionByConditionForPage(Map<String,Object> map);

    int queryCountOfTransactionByCondition(Map<String,Object> map);

    int saveCreateTran(Tran tran);

    int updateTransactionByTranId(Tran tran);

    Tran queryTransactionById(String id);

    int deleteTransactionByIds(String [] ids);

    List<Tran> queryTransactionByContactsId(String contactsId);

    List<Tran> queryTransactionByCustomerId(String customerId);

    Tran queryTranForDetailById(String id);

    List<FunnelVO> queryCountOfTranGroupByStage();
}
