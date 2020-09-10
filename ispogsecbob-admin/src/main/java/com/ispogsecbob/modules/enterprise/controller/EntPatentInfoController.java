package com.ispogsecbob.modules.enterprise.controller;

import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntPatentInfoEntity;
import com.ispogsecbob.modules.enterprise.service.EntPatentInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 企业/教师知识产权信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@RestController
@RequestMapping("enterprise/patent")
public class EntPatentInfoController {
    @Autowired
    private EntPatentInfoService entPatentInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:patent:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entPatentInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{patent InfoId}")
    @RequiresPermissions("enterprise:patent:info")
    public R info(@PathVariable("patent InfoId") Long patentInfoId){
		EntPatentInfoEntity entPatentInfo = entPatentInfoService.selectById(patentInfoId);

        return R.ok().put("entPatentInfo", entPatentInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enterprise:patent:save")
    public R save(@RequestBody EntPatentInfoEntity entPatentInfo){
		entPatentInfoService.insert(entPatentInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:patent:update")
    public R update(@RequestBody EntPatentInfoEntity entPatentInfo){
		entPatentInfoService.updateById(entPatentInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:patent:delete")
    public R delete(@RequestBody Long[] patentInfoIds){
		entPatentInfoService.deleteBatchIds(Arrays.asList(patentInfoIds));

        return R.ok();
    }

}
