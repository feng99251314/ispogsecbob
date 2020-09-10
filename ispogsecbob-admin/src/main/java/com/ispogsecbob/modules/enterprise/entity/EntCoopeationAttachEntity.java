package com.ispogsecbob.modules.enterprise.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 合作附件表
 * 
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-11-12 15:45:44
 */
@Data
@TableName("ent_coopeation_attach")
public class EntCoopeationAttachEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long cooperationAttachId;
	/**
	 * 合作信息外键
	 */
	private Long proCooperationInfoId;
	/**
	 * 附件名
	 */
	private String attachName;
	/**
	 * 文件路径
	 */
	private String url;

}
