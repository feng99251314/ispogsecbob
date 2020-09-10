package com.ispogsecbob.modules.enterprise.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 学生所获奖励/证书信息表
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-10-11 20:55:46
 */
@Data
@TableName("ent_student_achievement_info")
public class EntStudentAchievementInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long stuAchievementId;
	/**
	 * 学生基本信息表外键
	 */
	private Long userPerId;
	/**
	 * 所获奖励/证书内容
	 */
	private String stuAchievementContent;
	/**
	 * 奖励/证书获得时间
	 */
	private Date achievementTime;
	/**
	 * 奖励/证书获得地点
	 */
	private String achievementPlace;
	/**
	 * 奖励/证书级别:1校级 2省级 3国家级
	 */
	private Integer achievementAgree;
	/**
	 * 奖励类型:1学科竞赛，2专业比赛 ；   证书:1资格证，2等级证
	 */
	private Integer achievementType;
	/**
	 * 审核状态:0待审核 1已审核 2已通过 3未通过
	 */
	private Integer inApply;
	/**
	 * 不通过时的会回退信息
	 */
	private String retreatOption;
	/**
	 * 附件
	 * 		表示当前属性不是数据库的字段，但在项目中必须使用，这样在新增等使用bean的时候，mybatis-plus就会忽略这个，不会报错
	 */
	@TableField(exist = false,el = "com.ispogsecbob.modules.enterprise.entity.EntStudentAttachmentEntity")
	private EntStudentAttachmentEntity entStudentAttachmentEntity;

}
