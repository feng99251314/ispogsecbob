package com.ispogsecbob.modules.enterprise.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ispogsecbob.modules.enterprise.entity.EntProjectAttachEntity;
import com.ispogsecbob.modules.enterprise.config.ConfigApi;
import com.ispogsecbob.modules.enterprise.service.EntProjectAttachService;
import com.ispogsecbob.modules.enterprise.service.EntProjectInfoService;
import com.ispogsecbob.modules.util.FileDownFiles;
import com.ispogsecbob.modules.util.RandomUtils;
import com.ispogsecbob.modules.util.UpLoadFileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 项目附件表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-11-12 15:45:44
 */
@RestController
@RequestMapping("enterprise/project/attach")
public class EntProjectAttachController {
    @Autowired
    private EntProjectAttachService entProjectAttachService;
    @Autowired
    private EntProjectInfoService entProjectInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:entprojectattach:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entProjectAttachService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{projectAttachId}")
    @RequiresPermissions("enterprise:entprojectattach:info")
    public R info(@PathVariable("projectAttachId") Long projectAttachId){
		EntProjectAttachEntity entProjectAttach = entProjectAttachService.selectById(projectAttachId);

        return R.ok().put("entProjectAttach", entProjectAttach);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("enterprise:project:attachment:save")
    public R save(@RequestParam("file") List<MultipartFile> files, HttpServletRequest request) {

        String UPLOAD_FILES_PATH = ConfigApi.UPLOAD_URL + "/"+ RandomUtils.getRandomNums()+"/";

        if (Objects.isNull(files) || files.isEmpty()) {
            return R.error("文件为空，请重新上传");
        }

        EntProjectAttachEntity entProjectAttachEntity = null;

        for(MultipartFile file : files){
            String fileName = file.getOriginalFilename();
            try {
                UpLoadFileUtils.upLoad(UPLOAD_FILES_PATH, fileName, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            UPLOAD_FILES_PATH += fileName;
            entProjectAttachEntity = new EntProjectAttachEntity();
            entProjectAttachEntity.setAttachName(fileName);
            entProjectAttachEntity.setUrl(UPLOAD_FILES_PATH);
        }
        return R.ok("文件上传成功").put("entProjectAttachEntity", entProjectAttachEntity);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:entprojectattach:update")
    public R update(@RequestBody EntProjectAttachEntity entProjectAttach){
		entProjectAttachService.updateById(entProjectAttach);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:entprojectattach:delete")
    public R delete(@RequestBody Long[] projectAttachIds){
		entProjectAttachService.deleteBatchIds(Arrays.asList(projectAttachIds));

        return R.ok();
    }

    /**
     * 文件下载
     */
    @PostMapping(value = "/download")
    @RequiresPermissions("ispogsecbob:check:list")
    public void downloadFile(final HttpServletResponse response, final HttpServletRequest request) {
        String filePath = request.getParameter("filePath");
        FileDownFiles.download(response, filePath);
    }

}
