package com.ispogsecbob.modules.fabric.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import lombok.Data;

/**
 * 
 * 
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-08-06 09:52:59
 */
@Data
@TableName("ent_fabric_file")
public class EntFabricFileEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * 文件名
	 */
	private String fileName;
	/**
	 * 保存路径
	 */
	private String filePath;
	/**
	 * 文件哈希
	 */
	private String fileHash;
	/**
	 * 保存时间
	 */
	private Date fileTime;
	/**
	 * 拥有者
	 */
	private Long userId;
	/**
	 * 状态
	 */
	private Integer status;
	/**
	 * 对应的用户
	 */
	@TableField(exist = false)
	private SysUserEntity sysUserEntity;
	/**
	 * 已经审批的用户类型+id
	 */
	private String allowUser;
}
