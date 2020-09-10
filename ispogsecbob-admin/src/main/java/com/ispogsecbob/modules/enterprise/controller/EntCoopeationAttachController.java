package com.ispogsecbob.modules.enterprise.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ispogsecbob.modules.enterprise.entity.EntCoopeationAttachEntity;
import com.ispogsecbob.modules.enterprise.service.EntCoopeationAttachService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;



/**
 * 合作附件表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-11-12 15:45:44
 */
@RestController
@RequestMapping("enterprise/entcoopeationattach")
public class EntCoopeationAttachController {
    @Autowired
    private EntCoopeationAttachService entCoopeationAttachService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:entcoopeationattach:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entCoopeationAttachService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{cooperationAttachId}")
    @RequiresPermissions("enterprise:entcoopeationattach:info")
    public R info(@PathVariable("cooperationAttachId") Long cooperationAttachId){
		EntCoopeationAttachEntity entCoopeationAttach = entCoopeationAttachService.selectById(cooperationAttachId);

        return R.ok().put("entCoopeationAttach", entCoopeationAttach);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enterprise:entcoopeationattach:save")
    public R save(@RequestBody EntCoopeationAttachEntity entCoopeationAttach){
		entCoopeationAttachService.insert(entCoopeationAttach);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:entcoopeationattach:update")
    public R update(@RequestBody EntCoopeationAttachEntity entCoopeationAttach){
		entCoopeationAttachService.updateById(entCoopeationAttach);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:entcoopeationattach:delete")
    public R delete(@RequestBody Long[] cooperationAttachIds){
		entCoopeationAttachService.deleteBatchIds(Arrays.asList(cooperationAttachIds));

        return R.ok();
    }

}
