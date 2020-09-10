package com.ispogsecbob.modules.enterprise.service;

import com.baomidou.mybatisplus.service.IService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.modules.enterprise.entity.EntStudentAttachmentEntity;

import java.util.List;
import java.util.Map;

/**
 * 学生附件表
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-10-11 20:55:46
 */
public interface EntStudentAttachmentService extends IService<EntStudentAttachmentEntity> {

    PageUtils queryPage(Map<String, Object> params);

    //  学生信息外键
    EntStudentAttachmentEntity findByUserPerId(Long userPerId);
    //  项目信息外键
    EntStudentAttachmentEntity findByProInfoId(Long proInfoId);
    //  项目合作外键
    EntStudentAttachmentEntity findByProCooperationInfoId(Long proCooperationInfoId);
    //  学生工作/项目经历外键
    List<EntStudentAttachmentEntity> findByStuExperienceId(Long stuExperienceId);
    //  学生所获奖励/证书外键
    EntStudentAttachmentEntity findByStuAchievementId(Long stuAchievementId);

    //  学生工作/项目经历外键
    void deleteBatchStuExperienceIds(Long[] stuExperienceIds);
    //  学生所获奖励/证书外键
    void deleteBatchStuAchievementIds(Long[] stuAchievementIds);
}

