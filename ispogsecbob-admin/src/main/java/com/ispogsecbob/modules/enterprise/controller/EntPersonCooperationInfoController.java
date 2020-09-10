package com.ispogsecbob.modules.enterprise.controller;

import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.annotation.HasAdminRole;
import com.ispogsecbob.modules.enterprise.entity.EntPersonCooperationInfoEntity;
import com.ispogsecbob.modules.enterprise.entity.EntProjectCooperationInfoEntity;
import com.ispogsecbob.modules.enterprise.service.EntPersonCooperationInfoService;
import com.ispogsecbob.modules.enterprise.service.EntProjectCooperationInfoService;
import com.ispogsecbob.modules.enterprise.service.EntProjectInfoService;
import com.ispogsecbob.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 项目合作人表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@RestController
@RequestMapping("enterprise/person/cooperation")
public class EntPersonCooperationInfoController extends AbstractController {
    @Autowired
    private EntPersonCooperationInfoService entPersonCooperationInfoService;

    @Autowired
    private EntProjectInfoService entProjectInfoService;

    @Autowired
    private EntProjectCooperationInfoService entProjectCooperationInfoService;

    /**
     * 列表
     */
    @HasAdminRole(
            targetType = java.util.Map.class,
            index = 0, roleIds = {"9","10"}, perRoleId = "11",
            perRoleKey = "user_per_id", teacherRoleId = "12",
            teacherRoleKey = "user_teacher_id", entRoleId = "7",entRoleKey = "ent_info_id"
    )
    @RequestMapping("/list")
    // @RequiresPermissions("enterprise:person:cooperation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entPersonCooperationInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 列表
     */
    @HasAdminRole(
            targetType = java.util.Map.class,
            index = 0, roleIds = {"9","10"}, perRoleId = "11",
            perRoleKey = "user_per_id", teacherRoleId = "12",
            teacherRoleKey = "user_teacher_id", entRoleId = "7",entRoleKey = "ent_info_id"
    )
    @RequestMapping("/proList")
    // @RequiresPermissions("enterprise:project:info:list")
    public R proList(@RequestParam Map<String, Object> params){
        PageUtils page = entPersonCooperationInfoService.queryPageList(params);

        return R.ok().put("page", page);
    }

    /**
     * 我的参与的合作项目
     * @return
     */
    @HasAdminRole(
            targetType = java.util.Map.class,
            index = 0, roleIds = {"9","10"}, perRoleId = "11",
            perRoleKey = "user_per_id", teacherRoleId = "12",
            teacherRoleKey = "user_teacher_id", entRoleId = "7",entRoleKey = "ent_info_id"
    )
    @RequestMapping("/queryMyProject")
    public R queryMyProject(@RequestParam Map<String, Object> params){
        return entPersonCooperationInfoService.queryMyPersonProject(params);
    }

    /**
     * 我发布的合作项目
     * @return
     */
    @HasAdminRole(
            targetType = java.util.Map.class,
            index = 0, roleIds = {"9","10"}, perRoleId = "11",
            perRoleKey = "user_per_id", teacherRoleId = "12",
            teacherRoleKey = "user_teacher_id", entRoleId = "7",entRoleKey = "ent_info_id"
    )
    @RequestMapping("/queryProject")
    public R queryProject(@RequestParam Map<String, Object> params){
        return entPersonCooperationInfoService.queryPersonProject(params);
    }

//    /**
//     * 信息
//     */
//    @RequestMapping("/info/{proCooperationInfoId}/{inType}/{inApply}")
//    // @RequiresPermissions("enterprise:person:cooperation:info")
//    public R info(@PathVariable("proCooperationInfoId") Long proCooperationInfoId, @PathVariable("inType") String inType, @PathVariable("inApply") String inApply){
//		// EntPersonCooperationInfoEntity entPersonCooperationInfo = entPersonCooperationInfoService.selectById(proCooperationId);
//        //return R.ok().put("entPersonCooperationInfo", entPersonCooperationInfo);
//        EntProjectCooperationInfoEntity entProjectCooperationInfoEntity = entPersonCooperationInfoService.queryPersonCooperationInfoList(proCooperationInfoId, inType, inApply);
//        return R.ok().put("entProjectCooperationInfoEntity", entProjectCooperationInfoEntity);
//    }

    /**
     * 选择合作者
     */
    @RequestMapping("/info/{proCooperationInfoId}/{inType}/{inApply}")
    // @RequiresPermissions("enterprise:person:cooperation:info")
    public R queryPersonCooperationInfoListBySelect(@PathVariable("proCooperationInfoId") Long proCooperationInfoId, @PathVariable("inType") String inType, @PathVariable("inApply") String inApply){
        EntProjectCooperationInfoEntity entProjectCooperationInfoEntity = entPersonCooperationInfoService.queryPersonCooperationInfoList(proCooperationInfoId, inType, inApply);
        return R.ok().put("entProjectCooperationInfoEntity", entProjectCooperationInfoEntity);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("enterprise:person:cooperation:save")
    public R save(Long proInfoId){
		return entPersonCooperationInfoService.savePersonCooper(proInfoId, getUserId());
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("enterprise:person:cooperation:update")
    public R update(@RequestParam Map<String,Object> params){
		return entPersonCooperationInfoService.updatePersonCooperation(params);
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:person:cooperation:delete")
    public R delete(@RequestBody Long[] proCooperationIds){
		entPersonCooperationInfoService.deleteBatchIds(Arrays.asList(proCooperationIds));

        return R.ok();
    }

    /**
     * 删除全部合作人员
     */
    @RequestMapping("/deleteCoId")
    // @RequiresPermissions("enterprise:person:cooperation:delete")
    public R deleteByProjectCoId(@RequestParam Map<String,Object> params){
        Long proCooperationId = Long.valueOf(params.get("proCooperationInfoId").toString());
        List<Long> longs = entPersonCooperationInfoService.queryProCooperationId(proCooperationId);
        if(longs != null && longs.size() > 0){
            for(Long id : longs){
                entPersonCooperationInfoService.deleteById(id);
            }
        }
        return R.ok();
    }

    /**
     * 我的合作申请
     */
    @RequestMapping("/queryMyApply")
    public R queryMyApply(@RequestParam Map<String, Object> params){
        //return entProjectCooperationInfoService.queryProjectCooperationInfo(params);
        return entPersonCooperationInfoService.queryMyApplyPage(params, getUserId());
    }

    /**
     * 我参与的项目
     */
    @RequestMapping("/queryMyCooperation")
    public R queryMyCooperation(@RequestParam Map<String, Object> params){
        //return entProjectCooperationInfoService.queryProjectCooperationInfo(params);
        return entPersonCooperationInfoService.queryMyCooperationPage(params, getUserId());
    }
}
