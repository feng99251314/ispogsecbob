package com.ispogsecbob.modules.enterprise.service;


import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.modules.enterprise.entity.EntPatentInfoEntity;

import java.util.Map;

/**
 * 企业/教师知识产权信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
public interface EntPatentInfoService extends IService<EntPatentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

