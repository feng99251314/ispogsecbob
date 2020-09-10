package com.ispogsecbob.modules.enterprise.controller;

import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntTeacherAchievementInfoEntity;
import com.ispogsecbob.modules.enterprise.entity.EntTeacherAttachmentEntity;
import com.ispogsecbob.modules.enterprise.service.EntTeacherAchievementInfoService;
import com.ispogsecbob.modules.enterprise.service.EntTeacherAttachmentService;
import com.ispogsecbob.modules.enterprise.entity.EntUserTeacherInfoEntity;
import com.ispogsecbob.modules.enterprise.service.UserTeacherInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 教师所获奖励信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@RestController
@RequestMapping("enterprise/teacher/achievement")
public class EntTeacherAchievementInfoController {

    @Autowired
    private EntTeacherAchievementInfoService entTeacherAchievementInfoService;
    @Autowired
    private EntTeacherAttachmentService entTeacherAttachmentService;
    @Autowired
    private UserTeacherInfoService userTeacherInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:teacher:achievement:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entTeacherAchievementInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{teaAchievementId}")
    @RequiresPermissions("enterprise:teacher:achievement:info")
    public R info(@PathVariable("teaAchievementId") Long teaAchievementId){

		EntTeacherAchievementInfoEntity entTeacherAchievementInfo = entTeacherAchievementInfoService.selectById(teaAchievementId);

        EntTeacherAttachmentEntity attachmentEntity = entTeacherAttachmentService.findByTeaAchievementId(entTeacherAchievementInfo.getTeaAchievementId());

        entTeacherAchievementInfo.setEntTeacherAttachmentEntity(attachmentEntity);

        //教师信息
        EntUserTeacherInfoEntity teacherInfoEntity = userTeacherInfoService.queryByUserId(entTeacherAchievementInfo.getUserTeacherId());

        return R.ok().put("entTeacherAchievementInfo", entTeacherAchievementInfo).put("teacherInfo",teacherInfoEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enterprise:teacher:achievement:save")
    public R save(@RequestBody(required = false) EntTeacherAchievementInfoEntity entTeacherAchievementInfoEntity){

        entTeacherAchievementInfoService.save(entTeacherAchievementInfoEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:teacher:achievement:update")
    public R update(@RequestBody EntTeacherAchievementInfoEntity entTeacherAchievementInfo){
        //更新附件
        EntTeacherAttachmentEntity old = entTeacherAttachmentService.findByTeaAchievementId(entTeacherAchievementInfo.getTeaAchievementId());
        old.setTeaAttachmentName(entTeacherAchievementInfo.getEntTeacherAttachmentEntity().getTeaAttachmentName());
        old.setTeaAttachmentUrl(entTeacherAchievementInfo.getEntTeacherAttachmentEntity().getTeaAttachmentUrl());
        entTeacherAttachmentService.insertOrUpdate(old);

        entTeacherAchievementInfoService.updateById(entTeacherAchievementInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:teacher:achievement:delete")
    public R delete(@RequestBody Long[] teaAchievementIds){
		entTeacherAchievementInfoService.deleteBatchIds(Arrays.asList(teaAchievementIds));
        return R.ok();
    }

    /**
     * 审批
     * @param params
     * @return
     */
    @RequestMapping("/apply")
    public R apply(@RequestParam Map<String, Object> params){

        Long teaAchievementId = Long.parseLong(params.get("teaAchievementId").toString());

        Integer status = Integer.parseInt(params.get("status").toString());

        EntTeacherAchievementInfoEntity entity = entTeacherAchievementInfoService.selectById(teaAchievementId);

        if (status==3){ // 不通过意见
            String option = params.get("option").toString();
            entity.setRetreatOption(option);
        }

        entity.setInApply(status);

        entTeacherAchievementInfoService.insertOrUpdate(entity);

        return R.ok();
    }

}
