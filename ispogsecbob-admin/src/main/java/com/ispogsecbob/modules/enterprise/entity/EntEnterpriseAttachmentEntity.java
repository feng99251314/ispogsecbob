package com.ispogsecbob.modules.enterprise.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 企业附件表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@Data
@TableName("ent_enterprise_attach")
public class  EntEnterpriseAttachmentEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long entAttachmentId;
	/**
	 * 企业基本信息表外键
	 */
	private Long entInfoId;
	/**
	 * 项目信息外键
	 */
	private Long proInfoId;
	/**
	 * 项目合作外键
	 */
	private Long proCooperationInfoId;
	/**
	 * 招聘信息外键
	 */
	private Long recruitmentInfoId;
	/**
	 * 知识产权外键
	 */
	private Long patentInfoId;
	/**
	 * 文件路径
	 */
	private String entAttachmentUrl;
	/**
	 * 审核状态：0待审核，1已审核
	 */
	private String inApply;

}
