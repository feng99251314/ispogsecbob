package com.ispogsecbob.modules.enterprise.service;


import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntEnterpriseInfoEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 企业基本信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@Service
public interface EntEnterpriseInfoService extends IService<EntEnterpriseInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 企业信息详情
     * @param entInfoId
     * @return
     */
    R queryEntEnterpriseInfo(Long entInfoId, String inApply);

    /**
     * 更新企业入驻信息---审核
     * @return
     */
    R updateEntExamine(Map params);

    /**
     * 获取企业信息id 根据用户id
     * @param userId
     * @return
     */
    Long queryEntInfoIdByUserId(Long userId);

    /**
     * 查询userId，entInfoId指定值为ids
     * @param ids
     * @return
     */
    List<Long> queryUserIdByEntInfoId(Long[] ids);

    /**
     * 根据userId，查询企业信息
     * @param
     * @return
     */
    PageUtils queryListById(Map<String, Object> params);
}

