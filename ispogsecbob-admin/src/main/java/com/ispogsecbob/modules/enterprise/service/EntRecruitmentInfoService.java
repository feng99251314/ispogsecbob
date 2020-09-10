package com.ispogsecbob.modules.enterprise.service;


import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntRecruitmentInfoEntity;

import java.util.Map;

/**
 * 招聘信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
public interface EntRecruitmentInfoService extends IService<EntRecruitmentInfoEntity> {

    /**
     * 招聘信息表 --》 id 查招聘信息 + 企业信息 -- > 列表
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 招聘信息表 --》 id 查招聘信息 + 企业信息
     * @return
     */
    R entRecruitmentInfoById(Long recruitmentInfoId);

    /**
     * 更新招聘信息---审核
     * @param params
     * @return
     */
    R updateRecExamine(Map<String, Object> params);

}

