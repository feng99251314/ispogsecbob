package com.ispogsecbob.modules.enterprise.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ispogsecbob.modules.enterprise.entity.EntProjectCooperationInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目合作信息表
 * 
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@Mapper
public interface EntProjectCooperationInfoDao extends BaseMapper<EntProjectCooperationInfoEntity> {

    Integer queryCountPage(Map<String, Object> params);

    /**
     * 项目合作信息列表
     * @param params
     * @return
     */
    List<EntProjectCooperationInfoEntity> queryProjectCooperationInfoList(java.util.Map<String,Object> params);

    /**
     * 项目合作信息 学生详情
     * @param params
     * @return
     */
    EntProjectCooperationInfoEntity queryProjectCooperationInfoListForPer(java.util.Map<String,Object> params);

    /**
     * 项目合作信息 教师详情
     * @param params
     * @return
     */
    EntProjectCooperationInfoEntity queryProjectCooperationInfoListForTeacher(java.util.Map<String,Object> params);

    /**
     * 项目合作信息 企业详情
     * @param params
     * @return
     */
    EntProjectCooperationInfoEntity queryProjectCooperationInfoListForEnt(java.util.Map<String,Object> params);

    /**
     * 更新项目合作审核状态
     * @param params
     * @return
     */
    boolean updateProjectExamine(Map<String, Object> params);

    /**
     * 根据项目id查询合作信息
     * @param projectId
     * @return
     */
    EntProjectCooperationInfoEntity queryEntProjectCooperationInfoByProjectId(Long projectId);

    /**
     * 根据类型获取相关合作项目
     * @param type
     * @param id
     * @return
     */
    List<Long> queryProjectInfoIdByType(String type, long id);


    /**
     * 获取指定inApply的项目id
     * @param inApply
     * @return
     */
    List<Long> queryProInfoIdsByInApply(String inApply);

    /**
     * 列表项目是否有合作
     * @param projectIds
     * @return
     */
    ArrayList<Long> queryProInfoIdsByProInfoId(ArrayList<Long> projectIds);

    /**
     * 删除合作项目
     * @param proInfoId 项目id
     * @return
     */
    boolean deleteByProInfoId(Long proInfoId);

}
