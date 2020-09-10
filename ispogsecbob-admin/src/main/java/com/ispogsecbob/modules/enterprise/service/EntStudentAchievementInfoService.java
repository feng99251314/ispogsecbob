package com.ispogsecbob.modules.enterprise.service;

import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.modules.enterprise.entity.EntStudentAchievementInfoEntity;
import java.util.Map;

/**
 * 学生所获奖励/证书信息表
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-10-11 20:55:46
 */
public interface EntStudentAchievementInfoService extends IService<EntStudentAchievementInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询满足条件的项目总数
     * @param params
     * @return
     */
//    Integer queryCountPage(Map<String, Object> params);

    /**
     * 查询满足条件的项目
     * @param params
     * @return
     */
//    List<EntStudentAchievementInfoEntity> queryPage(Map<String, Object> params);
}

