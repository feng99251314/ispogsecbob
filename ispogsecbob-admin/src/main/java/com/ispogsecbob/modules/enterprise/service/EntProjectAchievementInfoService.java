package com.ispogsecbob.modules.enterprise.service;


import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntProjectAchievementInfoEntity;

import java.util.Map;

/**
 * 项目成果信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
public interface EntProjectAchievementInfoService extends IService<EntProjectAchievementInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryMyList(Map<String, Object> params);

    /**
     * 项目成果审核
     * @param params
     * @return
     */
    R updateProjectExamine(Map<String, Object> params);

    /**
     * 根据项目Id查询项目成果
     * @param params
     * @return
     */
    EntProjectAchievementInfoEntity queryProjectAchievementInfo(Map<String, Object> params);
}

