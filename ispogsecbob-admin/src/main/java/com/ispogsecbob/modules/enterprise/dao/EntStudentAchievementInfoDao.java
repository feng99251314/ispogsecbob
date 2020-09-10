package com.ispogsecbob.modules.enterprise.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ispogsecbob.modules.enterprise.entity.EntStudentAchievementInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生所获奖励/证书信息表
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-10-11 20:55:46
 */
@Mapper
public interface EntStudentAchievementInfoDao extends BaseMapper<EntStudentAchievementInfoEntity> {
//    Integer queryCountPage(Map<String, Object> params);
//    List<EntStudentAchievementInfoEntity> queryPage(Map<String, Object> params);
}
