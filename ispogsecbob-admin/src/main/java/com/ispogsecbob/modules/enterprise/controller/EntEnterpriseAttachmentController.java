package com.ispogsecbob.modules.enterprise.controller;

import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntEnterpriseAttachmentEntity;
import com.ispogsecbob.modules.enterprise.service.EntEnterpriseAttachmentService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 企业附件表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@RestController
@RequestMapping("enterprise/attachment")
public class EntEnterpriseAttachmentController {
    @Autowired
    private EntEnterpriseAttachmentService entEnterpriseAttachmentService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:attachment:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entEnterpriseAttachmentService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{entAttachmentId}")
    @RequiresPermissions("enterprise:attachment:info")
    public R info(@PathVariable("entAttachmentId") Long entAttachmentId){
		EntEnterpriseAttachmentEntity entEnterpriseAttachment = entEnterpriseAttachmentService.selectById(entAttachmentId);

        return R.ok().put("entEnterpriseAttachment", entEnterpriseAttachment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enterprise:attachment:save")
    public R save(@RequestBody EntEnterpriseAttachmentEntity entEnterpriseAttachment){
		entEnterpriseAttachmentService.insert(entEnterpriseAttachment);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:attachment:update")
    public R update(@RequestBody EntEnterpriseAttachmentEntity entEnterpriseAttachment){
		entEnterpriseAttachmentService.updateById(entEnterpriseAttachment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:attachment:delete")
    public R delete(@RequestBody Long[] entAttachmentIds){
		entEnterpriseAttachmentService.deleteBatchIds(Arrays.asList(entAttachmentIds));

        return R.ok();
    }

}
