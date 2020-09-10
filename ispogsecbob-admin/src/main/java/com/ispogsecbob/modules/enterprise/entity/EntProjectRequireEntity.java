package com.ispogsecbob.modules.enterprise.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 项目需求表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@Data
@TableName("ent_project_require")
public class EntProjectRequireEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long proRequireId;
	/**
	 * 项目信息外键
	 */
	private Long proInfoId;
	/**
	 * 项目需求人数
	 */
	private Integer proPeopleNumber;
	/**
	 * 需求专业
	 */
	private String requireSpecialty;
	/**
	 * 需求研究方向
	 */
	private String researchArea;
	/**
	 * 费用结算方式
	 */
	private String proPayoffWay;
	/**
	 * 学生基本信息表外键
	 */
	private Long stuInfoId;
	/**
	 * 教师基本信息表主键
	 */
	private Long teaInfoId;
	/**
	 * 审核状态：0待审核，1已审核
	 */
	private String inApply;
}
