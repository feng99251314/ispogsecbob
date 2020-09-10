package com.ispogsecbob.modules.enterprise.controller;

import java.util.Arrays;
import java.util.Map;

import com.ispogsecbob.modules.enterprise.entity.EntStudentAttachmentEntity;
import com.ispogsecbob.modules.enterprise.entity.EntUserPersonInfoEntity;
import com.ispogsecbob.modules.enterprise.service.EntStudentAttachmentService;
import com.ispogsecbob.modules.enterprise.service.UserPerInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispogsecbob.modules.enterprise.entity.EntStudentAchievementInfoEntity;
import com.ispogsecbob.modules.enterprise.service.EntStudentAchievementInfoService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;

/**
 * 学生所获奖励/证书信息表
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-10-11 20:55:46
 */
@RestController
@RequestMapping("enterprise/student/achievement")
public class EntStudentAchievementInfoController {
    @Autowired
    private EntStudentAchievementInfoService entStudentAchievementInfoService;
    //  附件信息
    @Autowired
    private EntStudentAttachmentService entStudentAttachmentService;
    @Autowired
    private UserPerInfoService userPerInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:student:achievement:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entStudentAchievementInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{stuAchievementId}")
    @RequiresPermissions("enterprise:student:achievement:list")
    public R info(@PathVariable("stuAchievementId") Long stuAchievementId){
		EntStudentAchievementInfoEntity entStudentAchievementInfo = entStudentAchievementInfoService.selectById(stuAchievementId);

        EntStudentAttachmentEntity entStudentAttachmentEntity = entStudentAttachmentService.findByStuAchievementId(entStudentAchievementInfo.getStuAchievementId());

        entStudentAchievementInfo.setEntStudentAttachmentEntity(entStudentAttachmentEntity);

        EntUserPersonInfoEntity perInfo = userPerInfoService.queryByUserId(entStudentAchievementInfo.getUserPerId());

        return R.ok().put("entStudentAchievementInfo", entStudentAchievementInfo).put("perInfo", perInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enterprise:student:achievement:save")
    public R save(@RequestBody(required = false)  EntStudentAchievementInfoEntity entStudentAchievementInfo){
		entStudentAchievementInfoService.insert(entStudentAchievementInfo);

        //  保存附件
        EntStudentAttachmentEntity entStudentAttachmentEntity = entStudentAchievementInfo.getEntStudentAttachmentEntity();
        entStudentAttachmentEntity.setStuAchievementId(entStudentAchievementInfo.getStuAchievementId());
        entStudentAttachmentService.insert(entStudentAttachmentEntity);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:student:achievement:update")
    public R update(@RequestBody(required = false)  EntStudentAchievementInfoEntity entStudentAchievementInfo){
		entStudentAchievementInfoService.updateById(entStudentAchievementInfo);

        //  修改附件
        EntStudentAttachmentEntity entStudentAttachmentEntity = entStudentAttachmentService.findByStuAchievementId(entStudentAchievementInfo.getStuAchievementId());
        if (entStudentAttachmentEntity != null) {
            entStudentAttachmentEntity.setStuAttachmentUrl(entStudentAchievementInfo.getEntStudentAttachmentEntity().getStuAttachmentUrl());
            entStudentAttachmentEntity.setStuAttachmentName(entStudentAchievementInfo.getEntStudentAttachmentEntity().getStuAttachmentName());
            entStudentAttachmentService.updateById(entStudentAttachmentEntity);
        } else {
            entStudentAttachmentEntity = entStudentAchievementInfo.getEntStudentAttachmentEntity();
            entStudentAttachmentEntity.setStuAchievementId(entStudentAchievementInfo.getStuAchievementId());
            entStudentAttachmentService.insert(entStudentAttachmentEntity);
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:student:achievement:delete")
    public R delete(@RequestBody Long[] stuAchievementIds){
		entStudentAchievementInfoService.deleteBatchIds(Arrays.asList(stuAchievementIds));

		//  删除附件
		entStudentAttachmentService.deleteBatchStuAchievementIds(stuAchievementIds);

        return R.ok();
    }

    /**
     * 审批
     */
    @RequestMapping("/apply")
    public R apply(@RequestParam Map<String, Object> params){

        String stuAchievementId = params.get("stuAchievementId").toString();

        Integer status = Integer.parseInt(params.get("status").toString());

        EntStudentAchievementInfoEntity entity = entStudentAchievementInfoService.selectById(stuAchievementId);

        entity.setInApply(status);

        if (status==3){ // 不通过意见
            String option = params.get("option").toString();
            entity.setRetreatOption(option);
        }

        entStudentAchievementInfoService.insertOrUpdate(entity);

        return R.ok();
    }

}
