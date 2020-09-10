package com.ispogsecbob.modules.enterprise.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 招聘学生表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@Data
@TableName("ent_project_student")
public class EntProjectStudentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long recruitmentId;
	/**
	 * 企业基本信息表主键
	 */
	private Long entInfoId;
	/**
	 * 学生基本信息表外键
	 */
	private Long stuInfoId;
	/**
	 * 审核状态：0待审核，1已审核
	 */
	private String inApply;
}
