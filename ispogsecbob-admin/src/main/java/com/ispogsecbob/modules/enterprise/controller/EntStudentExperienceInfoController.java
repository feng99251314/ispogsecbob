package com.ispogsecbob.modules.enterprise.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.ispogsecbob.modules.enterprise.entity.EntStudentAttachmentEntity;
import com.ispogsecbob.modules.enterprise.service.EntStudentAttachmentService;
import com.ispogsecbob.modules.enterprise.entity.EntUserPersonInfoEntity;
import com.ispogsecbob.modules.enterprise.service.UserPerInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispogsecbob.modules.enterprise.entity.EntStudentExperienceInfoEntity;
import com.ispogsecbob.modules.enterprise.service.EntStudentExperienceInfoService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;

/**
 * 学生工作/项目经历信息表
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-10-11 20:55:46
 */
@RestController
@RequestMapping("enterprise/student/experience")
public class EntStudentExperienceInfoController {
    @Autowired
    private EntStudentExperienceInfoService entStudentExperienceInfoService;
    @Autowired
    private EntStudentAttachmentService entStudentAttachmentService;
    @Autowired
    private UserPerInfoService userPerInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:student:experience:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entStudentExperienceInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{stuExperienceId}")
    @RequiresPermissions("enterprise:student:experience:list")
    public R info(@PathVariable("stuExperienceId") Long stuExperienceId){
		EntStudentExperienceInfoEntity entStudentExperienceInfo = entStudentExperienceInfoService.selectById(stuExperienceId);

        List<EntStudentAttachmentEntity> entStudentAttachmentEntities = entStudentAttachmentService.findByStuExperienceId(entStudentExperienceInfo.getStuExperienceId());

        entStudentExperienceInfo.setEntStudentAttachmentEntities(entStudentAttachmentEntities);

        EntUserPersonInfoEntity perInfo = userPerInfoService.queryByUserId(entStudentExperienceInfo.getUserPerId());

        return R.ok().put("entStudentExperienceInfo", entStudentExperienceInfo).put("perInfo", perInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enterprise:student:experience:save")
    public R save(@RequestBody EntStudentExperienceInfoEntity entStudentExperienceInfo){
		entStudentExperienceInfoService.insert(entStudentExperienceInfo);

        //  保存附件
        for (EntStudentAttachmentEntity attach : entStudentExperienceInfo.getEntStudentAttachmentEntities()) {
            attach.setStuExperienceId(entStudentExperienceInfo.getStuExperienceId());
            entStudentAttachmentService.insert(attach);
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:student:experience:update")
    public R update(@RequestBody EntStudentExperienceInfoEntity entStudentExperienceInfo){
		entStudentExperienceInfoService.updateById(entStudentExperienceInfo);

        //  先删除原附件
        Long[] stuExperienceIds = new Long[1];
        stuExperienceIds[0] = entStudentExperienceInfo.getStuExperienceId();
        entStudentAttachmentService.deleteBatchStuExperienceIds(stuExperienceIds);
        //  再保存附件
        for (EntStudentAttachmentEntity attach : entStudentExperienceInfo.getEntStudentAttachmentEntities()) {
            attach.setStuExperienceId(entStudentExperienceInfo.getStuExperienceId());
            entStudentAttachmentService.insert(attach);
        }

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:student:experience:delete")
    public R delete(@RequestBody Long[] stuExperienceIds){
		entStudentExperienceInfoService.deleteBatchIds(Arrays.asList(stuExperienceIds));

        //  删除附件
        entStudentAttachmentService.deleteBatchStuExperienceIds(stuExperienceIds);

        return R.ok();
    }

    /**
     * 审批
     */
    @RequestMapping("/apply")
    public R apply(@RequestParam Map<String, Object> params){

        String stuExperienceId = params.get("stuExperienceId").toString();

        Integer status = Integer.parseInt(params.get("status").toString());

        EntStudentExperienceInfoEntity entity = entStudentExperienceInfoService.selectById(stuExperienceId);

        entity.setInApply(status);

        // 不通过意见
        if (status==3){
            String option = params.get("option").toString();
            entity.setRetreatOption(option);
        }

        entStudentExperienceInfoService.insertOrUpdate(entity);

        return R.ok();
    }

}
