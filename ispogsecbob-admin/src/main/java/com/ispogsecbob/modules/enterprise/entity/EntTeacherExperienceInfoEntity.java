package com.ispogsecbob.modules.enterprise.entity;


import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

/**
 * 教师科研经历/成果信息表
 * 
 * @author Mikey
 * @email 1625017540@qq.com
 * @date 2019-10-10 10:10:39
 */
@Data
@TableName("ent_teacher_experience_info")
public class EntTeacherExperienceInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@TableId
	private Long teaExperienceId;
	/**
	 * 教师基本信息表外键
	 */
	private Long userTeacherId;
	/**
	 * 科研内容
	 */
	private String teaExperienceContent;
	/**
	 * 审核状态，0待审核 1已审核
	 */
	private Integer inApply;
	//不通过意见
	private String retreatOption;
	//附件entTeacherAttachmentEntities
	@TableField(exist = false,el = "com.ispogsecbob.modules.enterprise.entity.EntTeacherAttachmentEntity")
	private List<EntTeacherAttachmentEntity> entTeacherAttachmentEntities;
}
