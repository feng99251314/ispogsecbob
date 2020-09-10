package com.ispogsecbob.modules.enterprise.service;


import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntProjectInfoEntity;

import java.util.Map;

/**
 * 项目信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
public interface EntProjectInfoService extends IService<EntProjectInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 根据项目id和项目类型 查询项目信息 学生
     * @param id
     * @return
     */
    R queryEntProjectInfoByIdPerId(Long id);

    /**
     * 根据项目id和项目类型 查询项目信息 教师
     * @param id
     * @return
     */
    R queryEntProjectInfoByIdTeacherId(Long id);

    /**
     * 根据项目id和项目类型 查询项目信息 企业
     * @param id
     * @return
     */
    R queryEntProjectInfoByIdEntId(Long id);

    /**
     * 处理项目信息审核
     * @param params
     * @return
     */
    R updateEntExamine(Map<String, Object> params);

    /**
     * 项目关系信息 -- 返回项目实体，包含项目信息（发布者）、项目合作信息（合作者信息）
     * @param proInfoId
     * @param inType
     * @return
     */
    R queryProjectPersonCooperationInfo(Long proInfoId, String inType, String inApply);

    /**
     * 游客方式查询项目数据
     * @param params
     * @return
     */
    R queryWebEntProjectInfos(Map<String, Object> params);

    /**
     * 保存项目
     * @param entProjectInfoEntity
     * @return
     */
    R insertEntProject(EntProjectInfoEntity entProjectInfoEntity);

    /**
     * 用户 的 项目列表
     * @return
     */
    R queryPeojects();

    R queryPeojectsByAchieve();

    /**
     * 前端项目详情
     * @param projectId
     * @return
     */
    R queryWebEntProjectInfo(Long projectId, String inApply);
    /**
     * 该方法用于查询项目信息+附件信息+合作信息+负责人信息+成员信息+专利
     * @param proInfoId
     * @param inType
     * @return
     */
    R queryProjectInfoByTypeAndId(Long proInfoId, String inType);

    /**
     * 该方法用于查询项目信息+附件信息+合作信息+负责人信息+成员信息+专利
     * @param proInfoId
     * @param inType
     * @return
     */
    R queryProjectInfoByIdAndType(Long proInfoId, String inType);

    /**
     * 根据用户ID查询项目列表
     */
    PageUtils queryListByUserId(Map<String, Object> params);

    /**
     * 前端最新数据
     * @param params
     * @return
     */
    R queryNewWebEntProjectInfos(Map<String, Object> params);
}

