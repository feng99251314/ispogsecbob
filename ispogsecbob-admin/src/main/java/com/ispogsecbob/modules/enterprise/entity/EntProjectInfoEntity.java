package com.ispogsecbob.modules.enterprise.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 项目信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@Data
@TableName("ent_project_info")
public class EntProjectInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 项目信息主键
	 */
	@TableId
	private Long proInfoId;
	/**
	 * 项目名称
	 */
	private String proName;
	/**
	 * 项目登记
	 */
	private String proRegister;
	/**
	 * 项目来源
	 */
	private String proOrigin;
	/**
	 * 项目经费
	 */
	private String proOutlay;
	/**
	 * 项目类型
	 */
	private Integer proType;
	/**
	 * 项目介绍
	 */
	private String proIntroduce;

	/**
	 * 企业信息表外键
	 */
	private Long entInfoId;

	/**
	 * 学生信息表外键
	 */
	private Long userPerId;

	/**
	 * 师信息表外键
	 */
	private Long userTeacherId;

	/**
	 *审核状态，0待审核 1已审核
	 */
	private String inApply;

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
	 * 企业信息
	 */
	@TableField(exist = false)
	private EntEnterpriseInfoEntity entEnterpriseInfo;

	/**
	 * 用户数据
	 */
	@TableField(exist = false)
	private SysUserEntity sysUser;

	/**
	 * 项目附件信息
	 */
	@TableField(exist = false)
	private EntProjectAttachEntity entProjectAttachEntitie;


	/**
	 * 项目附件信息
	 */
	@TableField(exist = false)
	private List<EntProjectAttachEntity> entProjectAttachEntities;

	/**
	 * 项目合作信息
	 */
	@TableField(exist = false)
	private EntProjectCooperationInfoEntity projectCooperationInfo;

	/**
	 * 学生项目附件信息
	 */
	@TableField(exist = false)
	private List<EntStudentAttachmentEntity> studentAttachments;

	/**
	 * 教师项目附件信息
	 */
	@TableField(exist = false)
	private List<EntTeacherAttachmentEntity> teacherAttachments;

	/**
	 * 企业项目附件信息
	 */
	@TableField(exist = false)
	private EntEnterpriseAttachmentEntity enterpriseAttachment;

	/**
	 * 企业项目附件信息
	 */
	@TableField(exist = false)
	private List<EntEnterpriseAttachmentEntity> enterpriseAttachments;

	/**
	 * 项目成果信息集合
	 */
	@TableField(exist = false)
	private EntProjectAchievementInfoEntity entProjectAchievementInfo;

	/**
	 * 项目成果信息集合
	 */
	@TableField(exist = false)
	private List<EntProjectAchievementInfoEntity> entProjectAchievementInfoEntities;

}
