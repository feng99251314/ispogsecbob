package com.ispogsecbob.modules.enterprise.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.common.entity.CommonAttachments;
import com.ispogsecbob.modules.common.entity.CommonFile;
import com.ispogsecbob.modules.enterprise.entity.EntCoopeationAttachEntity;
import com.ispogsecbob.modules.enterprise.entity.EntProjectAchievementInfoEntity;
import com.ispogsecbob.modules.enterprise.entity.EntProjectAttachEntity;
import com.ispogsecbob.modules.enterprise.entity.EntProjectCooperationInfoEntity;
import com.ispogsecbob.modules.enterprise.service.EntProjectAchievementInfoService;
import com.ispogsecbob.modules.enterprise.service.EntProjectAttachService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.ispogsecbob.common.utils.ShiroUtils.getUserId;


/**
 * 项目成果信息表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@RestController
@RequestMapping("enterprise/project/achievement")
public class EntProjectAchievementInfoController {
    @Autowired
    private EntProjectAchievementInfoService entProjectAchievementInfoService;
    @Autowired
    private EntProjectAttachService entProjectAttachService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    //@RequiresPermissions("enterprise:project:achievement:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entProjectAchievementInfoService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 根据用户Id查找项目成果
     */
    @RequestMapping("/queryMyList")
    //@RequiresPermissions("enterprise:project:achievement:list")
    public R queryMyList(@RequestParam Map<String, Object> params){
        PageUtils page = entProjectAchievementInfoService.queryMyList(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{proAchievementId}/{inType}")
    //@RequiresPermissions("enterprise:project:achievement:info")
    public R info(@PathVariable("proAchievementId") Long proAchievementId, @PathVariable("inType") String inType){
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("inType", inType);
        params.put("proAchievementId",proAchievementId);
		EntProjectAchievementInfoEntity entProjectAchievementInfo = entProjectAchievementInfoService.queryProjectAchievementInfo(params);

        return R.ok().put("entProjectAchievementInfo", entProjectAchievementInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    // @RequiresPermissions("enterprise:project:achievement:save")
    public R save(@RequestBody Map<String, Object> params){
        Gson gson = new Gson();
        Object project = params.get("entProjectAchievementInfo");
        Object attach = params.get("attachments");

        // 解析项目数据，使用fastJson转换防止String类型的数据转换失败
        String json = gson.toJson(project);
        //EntProjectCooperationInfoEntity entProjectCooperationInfoEntity = gson.fromJson(json, EntProjectCooperationInfoEntity.class);
        JSONObject userJson = JSONObject.parseObject(json);
        EntProjectAchievementInfoEntity entProjectAchievementInfoEntity = JSON.toJavaObject(userJson,EntProjectAchievementInfoEntity.class);

        // 解析附件数据
        String jsonAttach = gson.toJson(attach);
        List<CommonAttachments> attachments = gson.fromJson(jsonAttach, new TypeToken<List<CommonAttachments>>(){}.getType());
        entProjectAchievementInfoService.insert(entProjectAchievementInfoEntity);

        if(attachments.size() > 0){
            List<EntProjectAttachEntity> insertBatch = new ArrayList<>();
            for(int i=0; i<attachments.size(); i++){
                CommonFile cfile = attachments.get(i).getResponse();
                EntProjectAttachEntity entProjectAttachEntity = new EntProjectAttachEntity();
                entProjectAttachEntity.setProInfoId(entProjectAchievementInfoEntity.getProInfoId());
                entProjectAttachEntity.setAttachName(attachments.get(i).getName());
                entProjectAttachEntity.setAttachType("2");
                entProjectAttachEntity.setUrl(cfile.getData());
                insertBatch.add(entProjectAttachEntity);
            }
            entProjectAttachService.insertBatch(insertBatch);
        }
        return R.ok().put("params", params);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:project:achievement:update")
    public R update(@RequestBody EntProjectAchievementInfoEntity entProjectAchievementInfo){
		entProjectAchievementInfoService.updateById(entProjectAchievementInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:project:achievement:delete")
    public R delete(@RequestBody Long[] proAchievementIds){
		entProjectAchievementInfoService.deleteBatchIds(Arrays.asList(proAchievementIds));

        return R.ok();
    }

    /**
     * 处理项目成果信息审核
     * @return
     */
    @RequestMapping("/entExamine")
    public R entExamine(@RequestParam  Map<String, Object> params){
        return entProjectAchievementInfoService.updateProjectExamine(params);
    }

}
