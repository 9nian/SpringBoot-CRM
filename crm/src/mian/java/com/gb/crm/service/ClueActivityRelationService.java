package com.gb.crm.workbench.service;



import com.gb.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {


    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> list);

    int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation);
}
