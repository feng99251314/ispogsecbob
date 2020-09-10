package com.ispogsecbob.modules.enterprise.service;


import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.enums.DefValueEnum;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.annotation.DefaultArrayValue;
import com.ispogsecbob.modules.enterprise.entity.EntPersonCooperationInfoEntity;
import com.ispogsecbob.modules.enterprise.entity.EntProjectCooperationInfoEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

/**
 * 项目合作人表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
public interface EntPersonCooperationInfoService extends IService<EntPersonCooperationInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     *  根据项目合作信息id,角色类型查询合作列表
     * @param proCooperationInfoId
     * @return
     */
    EntProjectCooperationInfoEntity queryPersonCooperationInfoList(Long proCooperationInfoId, String inType, String inApply);

    /**
     *  根据项目合作信息id,角色类型查询合作列表选择合作
     * @param proCooperationInfoId
     * @return
     */
    EntProjectCooperationInfoEntity queryPersonCooperationInfoListBySelect(Long proCooperationInfoId, String inType, String inApply);

    /**
     *  根据项目合作信息id查询合作关系人员信息
     * @param proCooperationInfoId
     * @return
     */
    List<EntPersonCooperationInfoEntity> queryPersonCooperationInfoByProCooperationInfoId(Long proCooperationInfoId);

    /**
     *  根据项目合作信息id查询合作关系人员信息
     * @param proCooperationInfoId
     * @return
     */
    List<EntPersonCooperationInfoEntity> queryPersonCooperationInfoByProCooperationInfoIdAndApply(Long proCooperationInfoId, String inApply);

    /**
     * 保存项目合作关系
     * @param proInfoId
     * @return
     */
    R savePersonCooper(Long proInfoId, Long userId);

    /**
     * 更新用户合作关系
     * @param params
     * @return
     */
    R updatePersonCooperation(Map<String, Object> params);

    /**
     * 合作关系
     * @param params
     * @return
     */
    PageUtils queryPageList(Map<String, Object> params);

    /**
     * 删除合作人员
     * @param proCooperationInfoId
     * @return
     */
    boolean deleteByProCooperationInfoId(Long proCooperationInfoId);

    /**
     * 查询项目合作用户编号
     * @return
     */
    List<Long> queryProCooperationId(Long proCooperationInfoId);

    /**
     * 我的参与的合作项目
     * @param params
     * @return
     */
    R queryMyPersonProject(Map<String, Object> params);

    /**
     * 我发布的合作项目
     * @param params
     * @return
     */
    R queryPersonProject(Map<String, Object> params);

    /**
     * 我的合作申请
     * @param params
     * @param userId
     * @return
     */
    R queryMyApplyPage(Map<String, Object> params, Long userId);

    /**
     * 我参与的项目
     * @param params
     * @param userId
     * @return
     */
    R queryMyCooperationPage(Map<String, Object> params, Long userId);
}

