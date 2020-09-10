package com.ispogsecbob.modules.enterprise.entity;


import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 项目合作信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@Data
@TableName("ent_project_cooperation_info")
public class EntProjectCooperationInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long proCooperationInfoId;
	/**
	 * 项目信息外键
	 */
	private Long proInfoId;
	/**
	 * 合作内容
	 */
	private String cooperationContent;
	/**
	 * 合作方式
	 */
	private String cooperationType;
	/**
	 * 合作要求
	 */
	private String cooperationRequire;
	/**
	 * 学生基本信息表外键：合作学生
	 */
	private Long userPerId;
	/**
	 * 教师基本信息表外键：合作老师
	 */
	private Long userTeacherId;
	/**
	 * 项目信息外键：合作企业
	 */
	private Long entInfoId;

	/**
	 * 合作状态，0待审核 1已审核 2已结束
	 */
	private String inApply;
	/**
	 * 发布类型，1科研项目，2横向项目，3大创项目，4企业招聘，5实习项目对接
	 */
	private String releaseType;
	/**
	 * 项目信息实体
	 */
	@TableField(exist = false)
	private EntProjectInfoEntity projectInfo;

	/**
	 * 企业信息
	 */
	@TableField(exist = false)
	private EntEnterpriseInfoEntity entEnterpriseInfo;

	/**
	 * 学生用户信息
	 */
	@TableField(exist = false)
	private EntUserPersonInfoEntity userPersonInfo;

	/**
	 * 教师用户信息
	 */
	@TableField(exist = false)
	private EntUserTeacherInfoEntity userTeacherInfo;

	/**
	 * 项目所有合作人
	 */
	@TableField(exist = false)
	private List<EntPersonCooperationInfoEntity> personCooperationInfos;

	/**
	 * 项目学生合作人
	 */
	@TableField(exist = false)
	private List<EntPersonCooperationInfoEntity> personCooperationPer;

	/**
	 * 项目教师合作人
	 */
	@TableField(exist = false)
	private List<EntPersonCooperationInfoEntity> personCooperationTeacher;

	/**
	 * 项目企业合作人
	 */
	@TableField(exist = false)
	private List<EntPersonCooperationInfoEntity> personCooperationEnt;

	/**
	 * 项目企业合作人
	 */
	@TableField(exist = false)
	private SysUserEntity sysUserEntity;

	/**
	 * 企业项目附件信息
	 */
	@TableField(exist = false)
	private List<EntEnterpriseAttachmentEntity> enterpriseAttachments;

	/**
	 * 项目附件信息
	 */
	@TableField(exist = false)
	private List<EntProjectAttachEntity> entProjectAttachEntities;

	/**
	 * 合作附件信息
	 */
	@TableField(exist = false)
	private List<EntCoopeationAttachEntity> entCoopeationAttachEntities;

	/**
	 * 项目学生合作人
	 */
	@TableField(exist = false)
	private List<EntUserPersonInfoEntity> userPersonInfoEntities;

	/**
	 * 项目教师合作人
	 */
	@TableField(exist = false)
	private List<EntUserTeacherInfoEntity> userTeacherInfoEntities;

	/**
	 * 项目企业合作人
	 */
	@TableField(exist = false)
	private List<EntEnterpriseInfoEntity> entEnterpriseInfoEntities;

}
