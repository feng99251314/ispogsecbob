package com.ispogsecbob.modules.enterprise.service;


import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.modules.enterprise.entity.EntEnterpriseAttachmentEntity;

import java.util.Map;

/**
 * 企业附件表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
public interface EntEnterpriseAttachmentService extends IService<EntEnterpriseAttachmentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

