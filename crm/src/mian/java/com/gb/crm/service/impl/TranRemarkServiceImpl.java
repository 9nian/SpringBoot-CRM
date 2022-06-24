package com.gb.crm.workbench.service.impl;

import com.gb.crm.workbench.domain.TranRemark;
import com.gb.crm.workbench.mapper.TranRemarkMapper;
import com.gb.crm.workbench.service.TranRemarkService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("tranRemarkService")
public class TranRemarkServiceImpl implements TranRemarkService {

    @Resource
    private TranRemarkMapper tranRemarkMapper;

    @Override
    public List<TranRemark> queryTranRemarkForDetailByTranId(String tranId) {
        return tranRemarkMapper.selectTranRemarkForDetailByTranId(tranId);
    }

    @Override
    public int updateTranRemarkById(TranRemark remark) {
        return tranRemarkMapper.updateTranRemarkById(remark);
    }
}
