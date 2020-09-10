package com.ispogsecbob.modules.enterprise.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 学生工作/项目经历信息表
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-10-11 20:55:46
 */
@Data
@TableName("ent_student_experience_info")
public class EntStudentExperienceInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Long stuExperienceId;
	/**
	 * 学生基本信息表外键
	 */
	private Long userPerId;
	/**
	 * 工作/项目经历内容
	 */
	private String stuExperienceContent;
	/**
	 * 工作/项目经历时间
	 */
	private Date stuExperienceTime;
	/**
	 * 工作/项目经历地点
	 */
	private String stuExperiencePlace;
	/**
	 * 工作/项目经历类型:1社会实践 2志愿者活动
	 */
	private Integer stuExperienceWorkType;
	/**
	 * 审核状态:0待审核 1已审核 2已通过 3未通过
	 */
	private Integer inApply;
	/**
	 * 不通过时的会回退信息
	 */
	private String retreatOption;
	/**
	 * 附件
	 * 		表示当前属性不是数据库的字段，但在项目中必须使用，这样在新增等使用bean的时候，mybatis-plus就会忽略这个，不会报错
	 */
	@TableField(exist = false,el = "com.ispogsecbob.modules.enterprise.entity.EntStudentAttachmentEntity")
	private List<EntStudentAttachmentEntity> entStudentAttachmentEntities;

}
