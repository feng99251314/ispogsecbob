package com.ispogsecbob.modules.enterprise.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ispogsecbob.modules.enterprise.entity.EntEnterpriseInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * 企业基本信息表
 * 
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@Mapper
public interface EntEnterpriseInfoDao extends BaseMapper<EntEnterpriseInfoEntity> {

    /**
     * 企业信息详情
     * @param entInfoId
     * @return
     */
    EntEnterpriseInfoEntity queryEntEnterpriseInfo(Long entInfoId, String inApply);


    /**
     * 更新企业入驻状态
     * @param entInfoId 企业基本信息表主键
     * @param inApply 企业入驻审核状态
     * @return
     */
    boolean updateInApply(Long entInfoId, String inApply);

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
}
