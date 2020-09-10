package com.ispogsecbob.modules.enterprise.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 学生附件表
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-10-11 20:55:46
 */
@Data
@TableName("ent_student_attach")
public class EntStudentAttachmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long stuAttachmentId;
	/**
	 * 学生基本信息表外键
	 */
	private Long userPerId;
	/**
	 * 项目信息外键
	 */
	private Long proInfoId;
	/**
	 * 项目合作外键
	 */
	private Long proCooperationInfoId;
	/**
	 * 学生工作/项目经历外键
	 */
	private Long stuExperienceId;
	/**
	 * 学生所获奖励/证书外键
	 */
	private Long stuAchievementId;
	/**
	 * 文件路径
	 */
	private String stuAttachmentUrl;
	//名称
	private String stuAttachmentName;
	/**
	 * 审核状态，0待审核 1已审核
	 */
	private String inApply;

}
