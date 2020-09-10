package com.ispogsecbob.modules.enterprise.service;


import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.modules.enterprise.entity.EntProjectStudentEntity;

import java.util.Map;

/**
 * 招聘学生表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
public interface EntProjectStudentService extends IService<EntProjectStudentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

