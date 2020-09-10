package com.ispogsecbob.modules.enterprise.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目成果信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@Data
@TableName("ent_project_achievement_info")
public class EntProjectAchievementInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long proAchievementId;
	/**
	 * 项目信息外键
	 */
	private Long proInfoId;
	/**
	 * 项目成果内容
	 */
	private String proAchievementContent;
	/**
	 * 审核状态：0待审核，1已审核
	 */
	private String inApply;
	/**
	 * 合作项目
	 */
	@TableField(exist = false)
	private EntProjectCooperationInfoEntity entProjectCooperationInfo;
	/**
	 * 项目信息
	 */
	@TableField(exist = false)
	private EntProjectInfoEntity entProjectInfo;
}
