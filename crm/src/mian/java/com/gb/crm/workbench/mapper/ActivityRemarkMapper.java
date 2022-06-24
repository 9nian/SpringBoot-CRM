package com.gb.crm.workbench.mapper;

import com.gb.crm.workbench.domain.ActivityRemark;

import java.util.List;

public interface ActivityRemarkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 11 18:03:38 CST 2022
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 11 18:03:38 CST 2022
     */
    int insert(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 11 18:03:38 CST 2022
     */
    int insertSelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 11 18:03:38 CST 2022
     */
    ActivityRemark selectByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 11 18:03:38 CST 2022
     */
    int updateByPrimaryKeySelective(ActivityRemark record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tbl_activity_remark
     *
     * @mbggenerated Sat Jun 11 18:03:38 CST 2022
     */
    int updateByPrimaryKey(ActivityRemark record);


    /**
     * 根据activityId查询该市场活动下所有备注的明细信息
     * @param activityId
     * @return
     */
    List<ActivityRemark> selectActivityRemarkForDetailByActivityId(String activityId);

    /**
     * 保存修改市场活动备注信息
     * @param activityRemark
     * @return
     */
    int insertActivityRemark(ActivityRemark activityRemark);

    /**
     * 根据id删除市场活动备注信息
     * @param id
     * @return
     */
    int deleteActivityRemarkById(String id);

    /**
     * 根据id修改市场活动备注信息
     * @param remark
     * @return
     */
    int updateActivityRemarkByID(ActivityRemark remark);
}
