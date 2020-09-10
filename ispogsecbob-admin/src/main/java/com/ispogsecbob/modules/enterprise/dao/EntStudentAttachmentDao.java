package com.ispogsecbob.modules.enterprise.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ispogsecbob.modules.enterprise.entity.EntStudentAttachmentEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 学生附件表
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-10-11 20:55:46
 */
@Mapper
public interface EntStudentAttachmentDao extends BaseMapper<EntStudentAttachmentEntity> {

    //  学生信息外键
    EntStudentAttachmentEntity findByUserPerId(Long userPerId);
    //  项目信息外键
    EntStudentAttachmentEntity findByProInfoId(Long proInfoId);
    //  项目合作外键
    EntStudentAttachmentEntity findByProCooperationInfoId(Long proCooperationInfoId);
    //  学生工作/项目经历外键
    EntStudentAttachmentEntity findByStuExperienceId(Long stuExperienceId);
    //  学生所获奖励/证书外键
    EntStudentAttachmentEntity findByStuAchievementId(Long stuAchievementId);
}
