package com.ispogsecbob.modules.fabric.controller;

import java.io.File;
import java.io.InputStream;
import java.util.*;

import com.ispogsecbob.common.utils.Constant;
import com.ispogsecbob.common.utils.HttpClientUtils;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import com.ispogsecbob.modules.sys.service.SysUserService;
import com.ispogsecbob.modules.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.ispogsecbob.modules.fabric.entity.EntFabricFileEntity;
import com.ispogsecbob.modules.fabric.service.EntFabricFileService;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.R;
import org.springframework.web.multipart.MultipartFile;
import proof.Node;
import proof.ProofUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Mikey
 * @email biaogejiushibiao@outlook.com
 * @date 2020-08-06 09:52:59
 */
@RestController
@RequestMapping("enterprise/fabric")
public class EntFabricFileController {


    private static Logger logger = LoggerFactory.getLogger(EntFabricFileController.class);


    @Autowired
    private EntFabricFileService entFabricFileService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("enterprise:fabric:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = entFabricFileService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("enterprise:fabric:info")
    public R info(@PathVariable("id") Long id) {
        EntFabricFileEntity entFabricFile = entFabricFileService.selectById(id);

        return R.ok().put("entFabricFile", entFabricFile);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("enterprise:fabric:save")
    public R save(@RequestBody EntFabricFileEntity entFabricFile) {
        entFabricFileService.insert(entFabricFile);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("enterprise:fabric:update")
    public R update(@RequestBody EntFabricFileEntity entFabricFile) {
        entFabricFileService.updateById(entFabricFile);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("enterprise:fabric:delete")
    public R delete(@RequestBody Long[] ids) {
        entFabricFileService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 文件上传
     *
     * @param files
     * @return
     */
    @PostMapping(value = "/upload")
//    @RequiresPermissions("enterprise:fabric:save")
    public Object uploadFile(@RequestParam("file") List<MultipartFile> files, @RequestParam("userId") Long userId) {

        String UPLOAD_FILES_PATH = Constant.SAVE_BLOCK_CHAIN_FILE_PATH + RandomUtils.getRandomNums() + "/";
        if (Objects.isNull(files) || files.isEmpty()) {
            return R.error("文件为空，请重新上传");
        }

        for (MultipartFile file : files) {
            try {
                //判断文件是否已经存在
                if (entFabricFileService.findBySHA256(JasyptUtils.getSHA_256(file.getInputStream())) != null) return R.error("凭证已经存在");

                String fileName = file.getOriginalFilename();
                UpLoadFileUtils.upLoad(UPLOAD_FILES_PATH, fileName, file);
                UPLOAD_FILES_PATH += fileName;
                EntFabricFileEntity fabricFileEntity = new EntFabricFileEntity();
                fabricFileEntity.setFilePath(UPLOAD_FILES_PATH);
                fabricFileEntity.setFileName(fileName);
                fabricFileEntity.setUserId(userId);//对应的用户

                fabricFileEntity.setFileHash(JasyptUtils.getSHA_256(new File(fabricFileEntity.getFilePath())));//文件哈希

                entFabricFileService.insert(fabricFileEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return R.ok();
    }

    /**
     * 校验文件
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/check")
    public Object check(@RequestParam("file") MultipartFile file) {

        EntFabricFileEntity entFabricFileEntity = null;

        String proof = null;

        try {
            String sha_256 = JasyptUtils.getSHA_256(file.getInputStream());

            entFabricFileEntity = entFabricFileService.findBySHA256(sha_256);

            if (entFabricFileEntity!=null) {

                SysUserEntity sysUserEntity = sysUserService.selectById(entFabricFileEntity.getUserId());

                proof = new ProofUtils().verifyProof(Node.valueOf(("CA"+(sysUserEntity.getFabricNodeType()+1))), sysUserEntity.getUserId().toString(), sha_256);
            }
        } catch (Exception e) {

            e.printStackTrace();

            return R.error(e.getMessage());

        }

        return R.ok().put("entFabricFileEntity", proof!=null?entFabricFileEntity:null);
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
     * 更新审核状态
     * @param params
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/apply")
    public R apply(@RequestParam Map<String, Object> params) throws Exception {

        Object id = params.get("id");
        Object userId = params.get("userId");

        EntFabricFileEntity fabricFileEntity = entFabricFileService.selectById(Integer.parseInt(id.toString()));

        fabricFileEntity.setStatus(fabricFileEntity.getStatus() + 1);

        SysUserEntity sysUserEntity = sysUserService.selectById(Integer.parseInt(userId.toString()));

        fabricFileEntity.setAllowUser(fabricFileEntity.getAllowUser()+"CA"+sysUserEntity.getFabricNodeType().toString()+userId+"|");

        entFabricFileService.updateById(fabricFileEntity);

        String saveProofRes = null;

        if (fabricFileEntity.getStatus() >= Constant.NUMBER_OF_CONSENSUS) {
            SysUserEntity userEntity = sysUserService.selectById(fabricFileEntity.getId());
            //存入区块链系统
            logger.info("存证信息存入区块链系统|" + fabricFileEntity.toString());
            //
            saveProofRes = new ProofUtils().saveProof(Node.valueOf("CA"+userEntity.getFabricNodeType().toString()), userEntity.getUserId().toString(), fabricFileEntity.getFileTime().toString(), fabricFileEntity.getFilePath(), fabricFileEntity.getFileHash(), fabricFileEntity.getUserId().toString());
        }

        return R.ok().put("saveProfres",saveProofRes);
    }

    public static void main(String[] args) {
        System.out.println(Node.valueOf("CA1"));
    }
}
