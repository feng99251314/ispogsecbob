package com.ispogsecbob.modules.enterprise.controller;

import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntProjectStudentEntity;
import com.ispogsecbob.modules.enterprise.service.EntProjectStudentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 招聘学生表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@RestController
@RequestMapping("enterprise/project/student")
public class EntProjectStudentController {
    @Autowired
    private EntProjectStudentService entProjectStudentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:project:student:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entProjectStudentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{recruitmentId}")
    @RequiresPermissions("enterprise:project:student:info")
    public R info(@PathVariable("recruitmentId") Long recruitmentId){
		EntProjectStudentEntity entProjectStudent = entProjectStudentService.selectById(recruitmentId);

        return R.ok().put("entProjectStudent", entProjectStudent);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enterprise:project:student:save")
    public R save(@RequestBody EntProjectStudentEntity entProjectStudent){
		entProjectStudentService.insert(entProjectStudent);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:project:student:update")
    public R update(@RequestBody EntProjectStudentEntity entProjectStudent){
		entProjectStudentService.updateById(entProjectStudent);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:project:student:delete")
    public R delete(@RequestBody Long[] recruitmentIds){
		entProjectStudentService.deleteBatchIds(Arrays.asList(recruitmentIds));

        return R.ok();
    }

}
