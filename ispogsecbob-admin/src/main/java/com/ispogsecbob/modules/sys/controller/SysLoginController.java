package com.ispogsecbob.modules.sys.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.common.entity.CommonAttachments;
import com.ispogsecbob.modules.common.entity.CommonFile;
import com.ispogsecbob.modules.enterprise.entity.EntEnterpriseAttachmentEntity;
import com.ispogsecbob.modules.enterprise.entity.EntEnterpriseInfoEntity;
import com.ispogsecbob.modules.enterprise.service.EntEnterpriseAttachmentService;
import com.ispogsecbob.modules.enterprise.service.EntEnterpriseInfoService;
import com.ispogsecbob.modules.enterprise.utils.SmsUtil;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import com.ispogsecbob.modules.sys.form.SysLoginForm;
import com.ispogsecbob.modules.sys.service.SysCaptchaService;
import com.ispogsecbob.modules.sys.service.SysUserService;
import com.ispogsecbob.modules.sys.service.SysUserTokenService;
import org.apache.commons.io.IOUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import proof.Node;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登录相关
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年11月10日 下午1:15:31
 */
@RestController
public class SysLoginController extends AbstractController {
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysUserTokenService sysUserTokenService;
	@Autowired
	private SysCaptchaService sysCaptchaService;
	@Autowired
	private EntEnterpriseInfoService entEnterpriseInfoService;

	@Autowired
	private EntEnterpriseAttachmentService entEnterpriseAttachmentService;

	private String VerificationCode;//短信验证码

	/**
	 * 验证码
	 */
	@GetMapping("captcha.jpg")
	public void captcha(HttpServletResponse response, String uuid)throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store, no-cache");
		response.setContentType("image/jpeg");

		//获取图片验证码
		BufferedImage image = sysCaptchaService.getCaptcha(uuid);

		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(image, "jpg", out);
		IOUtils.closeQuietly(out);
	}

	/**
	 * 登录
	 */
	@PostMapping("/sys/login")
	public Map<String, Object> login(@RequestBody SysLoginForm form)throws IOException {
		boolean captcha = sysCaptchaService.validate(form.getUuid(), form.getCaptcha());
		if(!captcha){
			return R.error("验证码不正确");
		}

		//用户信息
		SysUserEntity user = sysUserService.queryByUserName(form.getUsername());

		//账号不存在、密码错误
		if(user == null || !user.getPassword().equals(new Sha256Hash(form.getPassword(), user.getSalt()).toHex())) {
			return R.error("账号或密码不正确");
		}

		//账号锁定
		if(user.getStatus() == 0){
			return R.error("账号已被锁定,请联系管理员");
		}

		//生成token，并保存到数据库
		R r = sysUserTokenService.createToken(user.getUserId());
		return r;
	}

	/**
	 * 短信发送
	 */
	@PostMapping("/sys/message")
	public R save(@RequestBody String mobile) throws Exception{
		SmsUtil sms = new SmsUtil();
		VerificationCode = sms.getFourRandom();
		JSONObject object = new JSONObject(mobile);
		String phone = object.getString("mobile");
        System.out.println(VerificationCode);
		sms.sendSms(phone,VerificationCode);
		return R.ok();
	}

	/**
	 * 注册
	 * @param
	 * @return
	 */
	@PostMapping("/sys/register")
	@Transactional
	public R save(@RequestBody Map<String, Object> params) throws Exception{
		System.out.println(new Gson().toJson(params));
		String captcha = params.get("captcha").toString();
		SysUserEntity user = new SysUserEntity();
		if (captcha.equals(VerificationCode)){
			user.setUsername((String) params.get("username"));
			user.setName((String) params.get("name"));
			user.setPassword((String)params.get("password"));
			user.setMobile((String)params.get("mobile"));
			user.setEmail((String)params.get("email"));
			user.setCreateUserId(1L);
			user.setStatus(1);
			List<Long> longList = new ArrayList<Long>();
			//判断用户注册类型
			Long type = Long.parseLong((String) params.get("type"));
			if (1 == type){//项目负责人
				//区块链组织类型
				user.setFabricNodeType(Node.CA1.ordinal());
				longList.add(2L);
				longList.add(11L);
				user.setRoleIdList(longList);
				user.setInstituteId(Long.parseLong(params.get("instituteId").toString()));
			}else if(2 == type){//指导老师
				user.setFabricNodeType(Node.CA1.ordinal());
				longList.add(3L);
				longList.add(12L);
				user.setRoleIdList(longList);
				user.setInstituteId(Long.parseLong(params.get("instituteId").toString()));
			}else if (3 == type){//企业
				user.setFabricNodeType(Node.CA4.ordinal());
				longList.add(7L);
				user.setRoleIdList(longList);
			}
			sysUserService.save(user);
			if(3 == type){
				invokeEntMsg(params, user);
			}
		}else{
			return R.error("验证码错误");
		}
		return R.ok()
				.put("params", params);
	}

	/**
	 * 企业用户注册
	 * @param params
	 * @param user
	 */
	private void invokeEntMsg(@RequestBody Map<String, Object> params, SysUserEntity user) {
		Object ent = params.get("ent");
		Gson gson = new Gson();
		String json = gson.toJson(ent);
		EntEnterpriseInfoEntity entEntity = gson.fromJson(json, EntEnterpriseInfoEntity.class);
		entEntity.setUserId(user.getUserId());
		entEntity.setEntInTime(new Date());
		entEnterpriseInfoService.insert(entEntity);
		Object attach = params.get("attachments");
		String jsonAttach = gson.toJson(attach);
		List<CommonAttachments> attachments = gson.fromJson(jsonAttach, new TypeToken<List<CommonAttachments>>(){}.getType());
		if(attachments != null){
			List<EntEnterpriseAttachmentEntity> insertBatch = new ArrayList<>();
			for(int i=0; i<attachments.size(); i++){
				CommonFile cfile = attachments.get(i).getResponse();
				EntEnterpriseAttachmentEntity attachmentEntity = new EntEnterpriseAttachmentEntity();
				attachmentEntity.setEntInfoId(entEntity.getEntInfoId());
				attachmentEntity.setEntAttachmentUrl(cfile.getData());
				insertBatch.add(attachmentEntity);
			}
			entEnterpriseAttachmentService.insertBatch(insertBatch);
		}
	}


	/**
	 * 退出
	 */
	@PostMapping("/sys/logout")
	public R logout() {
		sysUserTokenService.logout(getUserId());
		return R.ok();
	}

}
