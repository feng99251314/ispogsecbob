package com.ispogsecbob.modules.enterprise.controller;

import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntProjectRequireEntity;
import com.ispogsecbob.modules.enterprise.service.EntProjectRequireService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 项目需求表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@RestController
@RequestMapping("enterprise/project/require")
public class EntProjectRequireController {
    @Autowired
    private EntProjectRequireService entProjectRequireService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:project:require:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entProjectRequireService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{proRequireId}")
    @RequiresPermissions("enterprise:project:require:info")
    public R info(@PathVariable("proRequireId") Long proRequireId){
		EntProjectRequireEntity entProjectRequire = entProjectRequireService.selectById(proRequireId);

        return R.ok().put("entProjectRequire", entProjectRequire);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enterprise:project:require:save")
    public R save(@RequestBody EntProjectRequireEntity entProjectRequire){
		entProjectRequireService.insert(entProjectRequire);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:project:require:update")
    public R update(@RequestBody EntProjectRequireEntity entProjectRequire){
		entProjectRequireService.updateById(entProjectRequire);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:project:require:delete")
    public R delete(@RequestBody Long[] proRequireIds){
		entProjectRequireService.deleteBatchIds(Arrays.asList(proRequireIds));

        return R.ok();
    }

}
