package com.gb.crm.workbench.service;


import com.gb.crm.workbench.domain.TranHistory;
import java.util.List;

public interface TranHistoryService {
    List<TranHistory> queryTranHistoryForDetailByTranId(String tranId);
}
