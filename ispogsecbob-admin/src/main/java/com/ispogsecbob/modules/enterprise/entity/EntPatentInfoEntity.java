package com.ispogsecbob.modules.enterprise.entity;


import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业/教师知识产权信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@Data
@TableName("ent_patent_info")
public class EntPatentInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long patentInfoId;
	/**
	 * 企业基本信息表外键
	 */
	private Long entInfoId;
	/**
	 * 教师基本信息表外键
	 */
	private Long userTeacherId;
	/**
	 * 学生基本信息表外键
	 */
	private Long userPerId;
	/**
	 * 专利号
	 */
	private String patentNo;
	/**
	 * 专利名称
	 */
	private String patentName;
	/**
	 * 发明人
	 */
	private String originator;
	/**
	 * 专利权人
	 */
	private String patentHolder;
	/**
	 * 授权公告日
	 */
	private Date patentTime;
	/**
	 * 项目信息外键
	 */
	private Long proInfoId;
	/**
	 * 审核状态：0待审核，1已审核
	 */
	private String inApply;
}
