package com.ispogsecbob.modules.enterprise.service;


import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.annotation.LimitPage;
import com.ispogsecbob.common.enums.DefValueEnum;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.annotation.DefaultArrayValue;
import com.ispogsecbob.modules.enterprise.entity.EntProjectCooperationInfoEntity;

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
public interface EntProjectCooperationInfoService extends IService<EntProjectCooperationInfoEntity> {
    /**
     * 项目合作信息 详情
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 项目合作信息 详情
     * @param params
     * @return
     */
    R queryProjectCooperationInfo(Map<String, Object> params);

    /**
     * 项目合作信息 详情 没有传入类型
     * @param params
     * @return
     */
    R queryProjectCooperationInfoNoType(Map<String, Object> params);

    /**
     * 项目合作审核
     * @param params
     * @return
     */
    R updateProjectExamine(Map<String, Object> params);

    /**
     * 根据项目id查询合作信息
     * @param projectId
     * @return
     */
    EntProjectCooperationInfoEntity queryEntProjectCooperationInfoByProjectId(Long projectId);

    /**
     * 新增项目合作关系
     * @param entProjectCooperationInfo
     * @return
     */
    R insertProjectCooperation(EntProjectCooperationInfoEntity entProjectCooperationInfo);

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

    ArrayList<Long> queryProInfoIdsByProInfoId(ArrayList<Long> projectIds);

    /**
     * 删除合作项目
     * @param proInfoId 项目id
     * @return
     */
    boolean deleteByProInfoId(Long proInfoId);

    /**
     * 我发布的合作
     * @param params
     * @param userId
     * @return
     */
    R queryProjectPage(Map<String, Object> params, Long userId);

    /**
     * 合作已审核
     * @param params
     * @param userId
     * @return
     */
    R queryCooperationByApplyPage(Map<String, Object> params, Long userId);
}

