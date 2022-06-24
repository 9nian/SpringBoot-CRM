package com.gb.crm.workbench.service;

import com.gb.crm.workbench.domain.TranRemark;

import java.util.List;

public interface TranRemarkService {
    List<TranRemark> queryTranRemarkForDetailByTranId(String tranId);

    int updateTranRemarkById(TranRemark remark);
}
