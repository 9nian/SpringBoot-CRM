package com.gb.crm.workbench.service.impl;

import com.gb.crm.workbench.domain.FunnelVO;
import com.gb.crm.workbench.domain.Tran;
import com.gb.crm.workbench.mapper.TranMapper;
import com.gb.crm.workbench.service.TranService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("tranService")
public class TranServiceImpl implements TranService {


    @Resource
    private TranMapper tranMapper;

    @Override
    public List<Tran> queryTransactionByConditionForPage(Map<String, Object> map) {
        return tranMapper.selectTransactionByConditionForPage(map);
    }

    @Override
    public int queryCountOfTransactionByCondition(Map<String, Object> map) {
        return tranMapper.selectCountOfTransactionByCondition(map);
    }

    @Override
    public int saveCreateTran(Tran tran) {
        return tranMapper.insertTran(tran);
    }

    @Override
    public int updateTransactionByTranId(Tran tran) {
        return tranMapper.updateTransactionByTranId(tran);
    }

    @Override
    public Tran queryTransactionById(String id) {
        return tranMapper.selectTransactionById(id);
    }

    @Override
    public int deleteTransactionByIds(String[] ids) {
        return tranMapper.deleteTransactionByIds(ids);
    }

    @Override
    public List<Tran> queryTransactionByContactsId(String contactsId) {
        return tranMapper.selectTransactionByContactsId(contactsId);
    }

    @Override
    public List<Tran> queryTransactionByCustomerId(String customerId) {
        return tranMapper.selectTransactionByCustomerId(customerId);
    }

    @Override
    public Tran queryTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

    @Override
    public List<FunnelVO> queryCountOfTranGroupByStage() {
        return tranMapper.selectCountOfTranGroupByStage();
    }
}
