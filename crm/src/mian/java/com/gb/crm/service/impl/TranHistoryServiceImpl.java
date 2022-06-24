package com.gb.crm.workbench.service.impl;

import com.gb.crm.workbench.domain.TranHistory;
import com.gb.crm.workbench.mapper.TranHistoryMapper;
import com.gb.crm.workbench.service.TranHistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("tranHistoryService")
public class TranHistoryServiceImpl implements TranHistoryService {

    @Resource
    private TranHistoryMapper tranHistoryMapper;
    @Override
    public List<TranHistory> queryTranHistoryForDetailByTranId(String tranId) {
        return tranHistoryMapper.selectTranHistoryForDetailByTranId(tranId);
    }
}
