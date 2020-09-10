package com.ispogsecbob.modules.enterprise.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.common.entity.CommonAttachments;
import com.ispogsecbob.modules.common.entity.CommonFile;
import com.ispogsecbob.modules.enterprise.annotation.HasAdminRole;
import com.ispogsecbob.modules.enterprise.entity.EntCoopeationAttachEntity;
import com.ispogsecbob.modules.enterprise.entity.EntProjectCooperationInfoEntity;
import com.ispogsecbob.modules.enterprise.entity.EntProjectInfoEntity;
import com.ispogsecbob.modules.enterprise.service.EntCoopeationAttachService;
import com.ispogsecbob.modules.enterprise.service.EntProjectCooperationInfoService;
import com.ispogsecbob.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 项目合作信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@RestController
@RequestMapping("enterprise/project/cooperation")
public class EntProjectCooperationInfoController extends AbstractController {
    @Autowired
    private EntProjectCooperationInfoService entProjectCooperationInfoService;
    @Autowired
    private EntCoopeationAttachService entCoopeationAttachService;

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
    //@RequiresPermissions("enterprise:project:cooperation:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entProjectCooperationInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 处理项目合作信息审核
     * @return
     */
    @RequestMapping("/entExamine")
    public R entExamine(@RequestParam  Map<String, Object> params){
        logger.info("接收数据:{}", params);
        return entProjectCooperationInfoService.updateProjectExamine(params);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{proCooperationInfoId}/{inType}")
    //@RequiresPermissions("enterprise:project:cooperation:info")
    public R info(@PathVariable("proCooperationInfoId") Long proCooperationInfoId, @PathVariable("inType") String inType){
		//EntProjectCooperationInfoEntity entProjectCooperationInfo = entProjectCooperationInfoService.selectById(proCooperationInfoId);
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("inType", inType);
        params.put("proCooperationInfoId",proCooperationInfoId);
        return entProjectCooperationInfoService.queryProjectCooperationInfo(params);
    }

    /**
     * 合作信息，没有传入类型
     */
    @RequestMapping("/infoNoType/{proCooperationInfoId}")
    //@RequiresPermissions("enterprise:project:cooperation:info")
    public R infoNoType(@PathVariable("proCooperationInfoId") Long proCooperationInfoId){
        //EntProjectCooperationInfoEntity entProjectCooperationInfo = entProjectCooperationInfoService.selectById(proCooperationInfoId);
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("proCooperationInfoId",proCooperationInfoId);
        return entProjectCooperationInfoService.queryProjectCooperationInfoNoType(params);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("enterprise:project:cooperation:save")
    public R save(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        Object project = params.get("Cooperation");
        Object attach = params.get("attachments");

        // 解析项目数据，使用fastJson转换防止String类型的数据转换失败
        String json = gson.toJson(project);
        //EntProjectCooperationInfoEntity entProjectCooperationInfoEntity = gson.fromJson(json, EntProjectCooperationInfoEntity.class);
        JSONObject userJson = JSONObject.parseObject(json);
        EntProjectCooperationInfoEntity entProjectCooperationInfoEntity = JSON.toJavaObject(userJson,EntProjectCooperationInfoEntity.class);

        // 解析附件数据
        String jsonAttach = gson.toJson(attach);
        List<CommonAttachments> attachments = gson.fromJson(jsonAttach, new TypeToken<List<CommonAttachments>>(){}.getType());
		entProjectCooperationInfoService.insertProjectCooperation(entProjectCooperationInfoEntity);

        if(attachments.size()>0){
            List<EntCoopeationAttachEntity> insertBatch = new ArrayList<>();
            for(int i=0; i<attachments.size(); i++){
                CommonFile cfile = attachments.get(i).getResponse();
                EntCoopeationAttachEntity entCoopeationAttachEntity = new EntCoopeationAttachEntity();
                entCoopeationAttachEntity.setProCooperationInfoId(entProjectCooperationInfoEntity.getProCooperationInfoId());
                entCoopeationAttachEntity.setAttachName(attachments.get(i).getName());
                entCoopeationAttachEntity.setUrl(cfile.getData());
                insertBatch.add(entCoopeationAttachEntity);
            }
            entCoopeationAttachService.insertBatch(insertBatch);
        }
        return R.ok().put("params", params);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("enterprise:project:cooperation:update")
    public R update(@RequestBody EntProjectCooperationInfoEntity entProjectCooperationInfo){
		entProjectCooperationInfoService.updateById(entProjectCooperationInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
   // @RequiresPermissions("enterprise:project:cooperation:delete")
    public R delete(@RequestBody Long[] proCooperationInfoIds){
		entProjectCooperationInfoService.deleteBatchIds(Arrays.asList(proCooperationInfoIds));

        return R.ok();
    }

    /**
     * 我发布的合作
     */
    @RequestMapping("/queryProject")
    public R queryProject(@RequestParam Map<String, Object> params){
        //return entProjectCooperationInfoService.queryProjectCooperationInfo(params);
        return entProjectCooperationInfoService.queryProjectPage(params, getUserId());
    }

    /**
     * 已审核的合作信息，包含状态1，2，3
     */
    @RequestMapping("/queryCooperationPage")
    public R queryCooperationPage(@RequestParam Map<String, Object> params){
        //return entProjectCooperationInfoService.queryProjectCooperationInfo(params);
        return entProjectCooperationInfoService.queryCooperationByApplyPage(params, getUserId());
    }


}
