package com.ispogsecbob.modules.enterprise.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 项目附件表
 * 
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-11-12 15:45:44
 */
@Data
@TableName("ent_project_attach")
public class EntProjectAttachEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long projectAttachId;
	/**
	 * 项目信息ID
	 */
	private Long proInfoId;
	/**
	 * 附件名称
	 */
	private String attachName;
	/**
	 * 附件类型：1项目信息，2专利
	 */
	private String attachType;
	/**
	 * 文件路径
	 */
	private String url;

}
