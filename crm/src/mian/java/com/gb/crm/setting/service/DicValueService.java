package com.gb.crm.setting.service;

import com.gb.crm.setting.domain.DicValue;

import java.util.List;

public interface DicValueService {
    List<DicValue> queryDicValueByTypeCode(String typeCode);
}
