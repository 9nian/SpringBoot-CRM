package com.gb.crm.workbench.mapper;

import com.gb.crm.workbench.domain.Clue;
import com.gb.crm.workbench.domain.FunnelVO;
import com.gb.crm.workbench.domain.Tran;

import java.util.List;
import java.util.Map;

public interface TranMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Wed Jun 15 11:32:02 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Wed Jun 15 11:32:02 CST 2022
     */
    int insert(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Wed Jun 15 11:32:02 CST 2022
     */
    int insertSelective(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Wed Jun 15 11:32:02 CST 2022
     */
    Tran selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Wed Jun 15 11:32:02 CST 2022
     */
    int updateByPrimaryKeySelective(Tran record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_tran
     *
     * @mbggenerated Wed Jun 15 11:32:02 CST 2022
     */
    int updateByPrimaryKey(Tran record);


    /**
     * 保存创建的交易
     * @param tran
     * @return
     */
    int insertTran(Tran tran);

    /**
     * 根据id查询交易的明细信息
     * @param id
     * @return
     */
    Tran selectTranForDetailById(String id);

    /**
     * 查询交易表中各个阶段的数据量
     * @return
     */
    List<FunnelVO> selectCountOfTranGroupByStage();


    /**
     * 根据条件查询交易信息
     * @param map
     * @return
     */
    List<Tran> selectTransactionByConditionForPage(Map<String,Object> map);

    /**
     * 根据条件查询交易的总条数
     * @param map
     * @return
     */
    int selectCountOfTransactionByCondition(Map<String,Object> map);

    /**
     * 根据id查询交易信息
     * @param id
     * @return
     */
    Tran selectTransactionById(String id);

    /**
     * 根据id修改交易信息
     * @param tran
     * @return
     */
    int updateTransactionByTranId(Tran tran);

    /**
     * 根据id删除多条交易
     * @param id
     * @return
     */
    int deleteTransactionByIds(String [] id);

    /**
     * 查询与联系人相关联的交易
     * @param contactsId
     * @return
     */
    List<Tran> selectTransactionByContactsId(String contactsId);

    /**
     * 查询与客户相关联的交易
     * @param customerId
     * @return
     */
    List<Tran> selectTransactionByCustomerId(String customerId);


}
