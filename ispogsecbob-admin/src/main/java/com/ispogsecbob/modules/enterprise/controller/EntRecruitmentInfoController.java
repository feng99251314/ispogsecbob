package com.ispogsecbob.modules.enterprise.controller;

import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntRecruitmentInfoEntity;
import com.ispogsecbob.modules.enterprise.service.EntEnterpriseInfoService;
import com.ispogsecbob.modules.enterprise.service.EntRecruitmentInfoService;
import com.ispogsecbob.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 招聘信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@RestController
@RequestMapping("enterprise/recruitment/info")
public class EntRecruitmentInfoController extends AbstractController {
    @Autowired
    private EntRecruitmentInfoService entRecruitmentInfoService;

    @Autowired
    private EntEnterpriseInfoService entEnterpriseInfoService;
    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("enterprise:recruitment:info:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entRecruitmentInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 处理招聘信息审核
     * @return
     */
    @RequestMapping("/recExamine")
    public R entExamine(@RequestParam  Map<String, Object> params){
        logger.info("接收数据:{}", params);
        return entRecruitmentInfoService.updateRecExamine(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{recruitmentInfoId}")
    //@RequiresPermissions("enterprise:recruitment:info")
    public R info(@PathVariable("recruitmentInfoId") Long recruitmentInfoId){
        R r = entRecruitmentInfoService.entRecruitmentInfoById(recruitmentInfoId);
        return r;
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("enterprise:recruitment:info:save")
    public R save( EntRecruitmentInfoEntity entRecruitmentInfo){
        Long userId = getUserId();
        Long entInfoId = entEnterpriseInfoService.queryEntInfoIdByUserId(userId);
        entRecruitmentInfo.setEntInfoId(entInfoId);
        entRecruitmentInfoService.insert(entRecruitmentInfo);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("enterprise:recruitment:info:update")
    public R update( EntRecruitmentInfoEntity entRecruitmentInfo){
		entRecruitmentInfoService.updateById(entRecruitmentInfo);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    // @RequiresPermissions("enterprise:recruitment:info:delete")
    public R delete(@RequestBody Long[] recruitmentInfoIds){
		entRecruitmentInfoService.deleteBatchIds(Arrays.asList(recruitmentInfoIds));

        return R.ok();
    }

}
