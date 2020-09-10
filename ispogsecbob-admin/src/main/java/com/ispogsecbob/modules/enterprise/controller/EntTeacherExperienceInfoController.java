package com.ispogsecbob.modules.enterprise.controller;

import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntTeacherAttachmentEntity;
import com.ispogsecbob.modules.enterprise.entity.EntTeacherExperienceInfoEntity;
import com.ispogsecbob.modules.enterprise.service.EntTeacherAttachmentService;
import com.ispogsecbob.modules.enterprise.service.EntTeacherExperienceInfoService;
import com.ispogsecbob.modules.enterprise.entity.EntUserTeacherInfoEntity;
import com.ispogsecbob.modules.enterprise.service.UserTeacherInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 教师科研经历/成果信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@RestController
@RequestMapping("enterprise/teacher/experience")
public class EntTeacherExperienceInfoController {
    @Autowired
    private EntTeacherExperienceInfoService entTeacherExperienceInfoService;
    @Autowired
    private EntTeacherAttachmentService entTeacherAttachmentService;
    @Autowired
    private UserTeacherInfoService userTeacherInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:teacher:experience:list")
    public R list(@RequestParam Map<String, Object> params){

        PageUtils page = entTeacherExperienceInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{teaExperienceId}")
    @RequiresPermissions("enterprise:teacher:experience:info")
    public R info(@PathVariable("teaExperienceId") Long teaExperienceId){

		EntTeacherExperienceInfoEntity entTeacherExperienceInfo = entTeacherExperienceInfoService.selectById(teaExperienceId);
        //教师
        List<EntTeacherAttachmentEntity> attachmentEntities = entTeacherAttachmentService.findByTeaExperienceId(entTeacherExperienceInfo.getTeaExperienceId());
        entTeacherExperienceInfo.setEntTeacherAttachmentEntities(attachmentEntities);
        //附件
        EntUserTeacherInfoEntity teacherInfoEntity = userTeacherInfoService.queryByUserId(entTeacherExperienceInfo.getUserTeacherId());

        return R.ok().put("entTeacherExperienceInfo", entTeacherExperienceInfo).put("teacherInfo",teacherInfoEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enterprise:teacher:experience:save")
    public R save(@RequestBody EntTeacherExperienceInfoEntity entTeacherExperienceInfo){
		entTeacherExperienceInfoService.insert(entTeacherExperienceInfo);
 		for(EntTeacherAttachmentEntity e:entTeacherExperienceInfo.getEntTeacherAttachmentEntities()){
		    e.setTeaExperienceId(entTeacherExperienceInfo.getTeaExperienceId());
		    entTeacherAttachmentService.insertOrUpdate(e);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:teacher:experience:update")
    public R update(@RequestBody EntTeacherExperienceInfoEntity entTeacherExperienceInfo){

		entTeacherExperienceInfoService.updateById(entTeacherExperienceInfo);
        //删除
		for (EntTeacherAttachmentEntity entity:entTeacherAttachmentService.findByTeaExperienceId(entTeacherExperienceInfo.getTeaExperienceId())){
		    entTeacherAttachmentService.deleteById(entity.getTeaAttachmentId());
        }
		//保存
		for(EntTeacherAttachmentEntity e:entTeacherExperienceInfo.getEntTeacherAttachmentEntities()){
            e.setTeaExperienceId(entTeacherExperienceInfo.getTeaExperienceId());
            entTeacherAttachmentService.insertOrUpdate(e);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:teacher:experience:delete")
    public R delete(@RequestBody Long[] teaExperienceIds){

		entTeacherExperienceInfoService.deleteBatchIds(Arrays.asList(teaExperienceIds));

        return R.ok();
    }

    /**
     * 审批
     * @param params
     * @return
     */
    @RequestMapping("/apply")
    public R apply(@RequestParam Map<String, Object> params){
        String teaExperienceId = params.get("teaExperienceId").toString();
        Integer status = Integer.parseInt(params.get("status").toString());

        EntTeacherExperienceInfoEntity entity = entTeacherExperienceInfoService.selectById(teaExperienceId);

        entity.setInApply(status);

        if (status==3){ // 不通过意见
            String option = params.get("option").toString();
            entity.setRetreatOption(option);
        }
        entTeacherExperienceInfoService.insertOrUpdate(entity);

        return R.ok();
    }
}
