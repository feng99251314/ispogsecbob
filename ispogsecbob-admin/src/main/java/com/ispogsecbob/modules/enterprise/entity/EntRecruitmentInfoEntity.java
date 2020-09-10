package com.ispogsecbob.modules.enterprise.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 招聘信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@Data
@TableName("ent_recruitment_info")
public class EntRecruitmentInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long recruitmentInfoId;

	/**
	 * 企业id
	 */
	private Long entInfoId;
	/**
	 * 工作内容
	 */
	private String jobContent;
	/**
	 * 招聘岗位
	 */
	private String recruitmentPost;
	/**
	 * 岗位要求
	 */
	private String postRequire;
	/**
	 * 招聘人数
	 */
	private Integer recruitmentPeopleNumber;
	/**
	 * 招聘专业
	 */
	private Integer recruitmentSpecialty;
	/**
	 * 工作方式
	 */
	private String workWay;
	/**
	 * 审核状态：0待审核，1已审核
	 */
	private String inApply;

	/**
	 * 企业基本信息表
	 */
	@TableField(exist = false)
	private EntEnterpriseInfoEntity entEnterpriseInfo;

}
