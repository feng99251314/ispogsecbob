package com.ispogsecbob.modules.enterprise.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 企业基本信息表
 * 
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@Data
@TableName("ent_enterprise_info")
public class EntEnterpriseInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 企业基本信息表主键
	 */
	@TableId
	private Long entInfoId;
	/**
	 * 用户外键
	 */
	private Long userId;
	/**
	 * 企业名称
	 */
	private String entName;
	/**
	 * 法人代表
	 */
	private String entCorporate;
	/**
	 * 经营范围
	 */
	private String entBusiness;
	/**
	 * 统一信用代码
	 */
	private String entCode;
	/**
	 * 注册资本
	 */
	private String entRegister;
	/**
	 * 成立时间
	 */
	private Date entFoundingTime;
	/**
	 * 企业类型
	 */
	private String entType;
	/**
	 * 经营状态:1存续(开业)，2在业，3吊销，4注销，5迁入，6迁出，7停业，8清算
	 */
	private Integer entStatus;
	/**
	 * 注册地址
	 */
	private String entRegisterAddress;
	/**
	 * 企业地址
	 */
	private String entAddress;
	/**
	 * 企业介绍
	 */
	private String entIntroduce;
	/**
	 * 是否高新区:0是，1否
	 */
	private String newHighZones;

	/**
	 * 企业头像
	 */
	private String entLogo;

	/**
	 * 入驻申请时间
	 */
	private Date entInTime;

	/**
	 * 是否审核
	 */
	private String inApply;

	/**
	 * 企业证明材料，附件
	 */
	@TableField(exist = false)
	private List<EntEnterpriseAttachmentEntity> attachments;


	/**
	 * 企业用户信息
	 */
	@TableField(exist = false)
	private SysUserEntity sysUser;

	/**
	 * 合作关系实体
	 */
	@TableField(exist = false)
	private EntPersonCooperationInfoEntity entPersonCooperationInfoEntity;


}
