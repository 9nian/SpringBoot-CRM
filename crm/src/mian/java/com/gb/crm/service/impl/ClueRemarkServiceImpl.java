package com.gb.crm.workbench.service.impl;

import com.gb.crm.workbench.domain.ClueRemark;
import com.gb.crm.workbench.mapper.ClueRemarkMapper;
import com.gb.crm.workbench.service.ClueRemarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("clueRemarkService")
public class ClueRemarkServiceImpl implements ClueRemarkService {

    @Resource
    ClueRemarkMapper clueRemarkMapper;

    @Override
    public List<ClueRemark> queryClueRemarkForDetailByClueId(String id) {
        return clueRemarkMapper.selectClueRemarkForDetailClueById(id);
    }

    @Override
    public int saveCreateClueRemark(ClueRemark remark) {
        return clueRemarkMapper.insertCreateClueRemark(remark);
    }

    @Override
    public int updateClueRemarkById(ClueRemark remark) {
        return clueRemarkMapper.updateClueRemarkById(remark);
    }
}
