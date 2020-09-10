package com.ispogsecbob.modules.enterprise.controller;

import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.entity.EntTeacherAttachmentEntity;
import com.ispogsecbob.modules.enterprise.service.EntTeacherAttachmentService;
import com.ispogsecbob.modules.enterprise.config.ConfigApi;
import com.ispogsecbob.modules.enterprise.entity.EntUserTeacherInfoEntity;
import com.ispogsecbob.modules.enterprise.service.UserTeacherInfoService;
import com.ispogsecbob.modules.util.FileDownFiles;
import com.ispogsecbob.modules.util.RandomUtils;
import com.ispogsecbob.modules.util.UpLoadFileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 教师附件表
 *
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:18:36
 */
@RestController
@RequestMapping("enterprise/teacher/attachment")
public class EntTeacherAttachmentController {
    @Autowired
    private EntTeacherAttachmentService entTeacherAttachmentService;
    @Autowired
    private UserTeacherInfoService userTeacherInfoService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:teacher:attachment:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = entTeacherAttachmentService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{teaAttachmentId}")
    @RequiresPermissions("enterprise:teacher:attachment:info")
    public R info(@PathVariable("teaAttachmentId") Long teaAttachmentId){
		EntTeacherAttachmentEntity entTeacherAttachment = entTeacherAttachmentService.selectById(teaAttachmentId);

        return R.ok().put("entTeacherAttachment", entTeacherAttachment);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
//    @RequiresPermissions("enterprise:teacher:attachment:save")
    public R save(@RequestParam("file") List<MultipartFile> files, HttpServletRequest request) {

        String teacherId = request.getParameter("teacherId");

        EntUserTeacherInfoEntity EntUserTeacherInfoEntity = userTeacherInfoService.queryByUserId(Long.parseLong(teacherId));

        String UPLOAD_FILES_PATH = ConfigApi.UPLOAD_URL + EntUserTeacherInfoEntity.getSysUserEntity().getName() + "/"+ RandomUtils.getRandomNums()+"/";

        if (Objects.isNull(files) || files.isEmpty()) {
            return R.error("文件为空，请重新上传");
        }

        EntTeacherAttachmentEntity entTeacherAttachmentEntity = null;

        for(MultipartFile file : files){
            String fileName = file.getOriginalFilename();
            try {
                UpLoadFileUtils.upLoad(UPLOAD_FILES_PATH, fileName, file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            UPLOAD_FILES_PATH += fileName;
            entTeacherAttachmentEntity = new EntTeacherAttachmentEntity();
            entTeacherAttachmentEntity.setTeaAttachmentName(fileName);
            entTeacherAttachmentEntity.setTeaAttachmentUrl(UPLOAD_FILES_PATH);
            entTeacherAttachmentEntity.setUserTeacherId(EntUserTeacherInfoEntity.getUserTeacherId());

        }
        return R.ok("文件上传成功").put("entTeacherAttachmentEntity", entTeacherAttachmentEntity);

    }

    /**
     * 文件下载
     */
    @PostMapping(value = "/download")
    public void downloadFile(final HttpServletResponse response, final HttpServletRequest request) {
        String filePath = request.getParameter("filePath");
        FileDownFiles.download(response, filePath);
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:teacher:attachment:update")
    public R update(@RequestBody EntTeacherAttachmentEntity entTeacherAttachment){
		entTeacherAttachmentService.updateById(entTeacherAttachment);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:teacher:attachment:delete")
    public R delete(@RequestBody Long[] teaAttachmentIds){
		entTeacherAttachmentService.deleteBatchIds(Arrays.asList(teaAttachmentIds));

        return R.ok();
    }

}
