package com.ispogsecbob.modules.boss.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ispogsecbob.common.annotation.SysLog;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.common.validator.Assert;
import com.ispogsecbob.modules.enterprise.service.EntProjectInfoService;
import com.ispogsecbob.modules.enterprise.service.UserPerInfoService;
import com.ispogsecbob.modules.enterprise.service.UserTeacherInfoService;
import com.ispogsecbob.modules.enterprise.utils.SmsUtil;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import com.ispogsecbob.modules.sys.form.PasswordForm;
import com.ispogsecbob.modules.sys.service.SysUserRoleService;
import com.ispogsecbob.modules.sys.service.SysUserService;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description 前端主页面控制器
 * @date 2019/10/15
 */
@RestController
@RequestMapping("webpage")
public class BossWebPageIndexController {

    @Autowired
    private EntProjectInfoService entProjectInfoService;

    @Autowired
    private SysUserService sysUserService;

    private String VerificationCode;//短信验证码

    @GetMapping("/projectInfos")
    public R projectInfos(@RequestParam Map<String, Object> params) {
        return entProjectInfoService.queryWebEntProjectInfos(params);
    }

    @GetMapping("/newProjectInfos")
    public R newProjectInfos(@RequestParam Map<String, Object> params) {
        return entProjectInfoService.queryNewWebEntProjectInfos(params);
    }

    /**
     * 项目详情
     *
     * @param projectId
     * @return
     */
    @GetMapping("/projectInfo/{projectId}")
    public R projectInfo(@PathVariable("projectId") Long projectId) {
        return entProjectInfoService.queryWebEntProjectInfo(projectId, "1");
    }

    /**
     * 短信发送
     */
    @PostMapping("/message")
    public R message(@RequestBody String mobile) throws Exception {
        SmsUtil sms = new SmsUtil();
        VerificationCode = sms.getFourRandom();
        JSONObject object = new JSONObject(mobile);
        String phone = object.getString("mobile");
        System.out.println(VerificationCode);
        sms.sendSms(phone, VerificationCode);
        return R.ok();
    }

    /**
     * 通过手机号码获取用户信息
     */
    @PostMapping("/mobile")
    public R mobile(@RequestBody String mobile) throws Exception {
        JSONObject object = new JSONObject(mobile);
        String phone = object.getString("mobile");
        List<SysUserEntity> users = sysUserService.queryByUserMobile(phone);
        String user = "";
        if (users.size() > 0) {
            for (int i = 0; i < users.size(); i++) {
                String user1 = users.get(i).getUsername();
                String user2 = user1 + " , ";
                user += user2;
            }
            user = user.substring(0, user.length() - 2);
        }
        return R.ok().put("user", user);
    }

    @RequestMapping("/update")
    public R updatePwd(@RequestParam(name = "tk", required = false) String tk,
                       @RequestParam(name = "pwd", required = false, defaultValue = "123456") String pwd,
                       @RequestParam(name = "ex", required = false) String ex,
                       @RequestParam(name = "type", required = false, defaultValue = "1") String type) {
        if (!"".equals(tk) && "1".equals(type)) {// 单个更新密码
            SysUserEntity sysUserEntity = sysUserService.queryByUserName(tk);
            if (sysUserEntity != null) {
                String newPwd = new Sha256Hash(pwd, sysUserEntity.getSalt()).toHex();
                boolean b = sysUserService.updatePassword(sysUserEntity.getUserId(), sysUserEntity.getPassword(), newPwd);
                return R.ok().put("statue", b);
            }
            return R.error("用户不存在");
        } else if ("2".equals(type) && ex != null) { // 更新指定多个人
            String[] split = ex.split(",");
            EntityWrapper<SysUserEntity> wrapper = new EntityWrapper<>();
            wrapper.in("username", split);
            List<SysUserEntity> list = sysUserService.selectList(wrapper);
            HashMap<Object, Object> res = new HashMap<>();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    SysUserEntity sysUserEntity = list.get(i);
                    String newPwd = new Sha256Hash(pwd, sysUserEntity.getSalt()).toHex();
                    boolean b = sysUserService.updatePassword(sysUserEntity.getUserId(), sysUserEntity.getPassword(), newPwd);
                    res.put(sysUserEntity.getUsername(), b);
                }
            }
            return R.ok().put("result", res);
        } else {// 更新全部
            EntityWrapper<SysUserEntity> wrapper = new EntityWrapper<>();
            if (ex != null) {
                String[] split = ex.split(",");
                wrapper.notIn("username", split);
            }
            List<SysUserEntity> list = sysUserService.selectList(wrapper);
            HashMap<Object, Object> res = new HashMap<>();
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    SysUserEntity sysUserEntity = list.get(i);
                    if ("SuperAdmin".equals(sysUserEntity.getUsername())) {
                        continue;
                    }
                    String newPwd = new Sha256Hash(pwd, sysUserEntity.getSalt()).toHex();
                    boolean b = sysUserService.updatePassword(sysUserEntity.getUserId(), sysUserEntity.getPassword(), newPwd);
                    res.put(sysUserEntity.getUsername(), b);
                }
            }
            return R.ok().put("result", res);
        }
    }

    /**
     * 账号/密码找回
     */
    @SysLog("修改密码")
    @PostMapping("/password")
    public R password(@RequestBody Map<String, Object> params) {
        Assert.isBlank(params.get("newPassword").toString(), "新密码不为能空");

        String captcha = params.get("captcha").toString();
        List<SysUserEntity> users = sysUserService.queryByUserMobile(params.get("mobile").toString());

        if (captcha.equals(VerificationCode)) {
            for (int i = 0; i < users.size(); i++) {
                //sha256加密
                String password = new Sha256Hash(params.get("password").toString(), users.get(i).getSalt()).toHex();
                //sha256加密
                String newPassword = new Sha256Hash(params.get("newPassword").toString(), users.get(i).getSalt()).toHex();
                //更新密码
                sysUserService.updatePassword(users.get(i).getUserId(), users.get(i).getPassword(), newPassword);
            }
        } else {
            return R.ok().put("come", "0");
        }
        return R.ok().put("come", "1");
    }
}
