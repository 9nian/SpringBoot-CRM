package com.gb.crm.workbench.service;

import com.gb.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkService {

    List<ClueRemark> queryClueRemarkForDetailByClueId(String id);

    int saveCreateClueRemark(ClueRemark remark);

    int updateClueRemarkById(ClueRemark remark);
}
