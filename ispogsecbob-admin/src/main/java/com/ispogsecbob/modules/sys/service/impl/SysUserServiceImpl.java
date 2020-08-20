package com.ispogsecbob.modules.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.ispogsecbob.common.exception.RRException;
import com.ispogsecbob.common.utils.Constant;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.Query;
import com.ispogsecbob.modules.sys.dao.SysUserDao;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import com.ispogsecbob.modules.sys.service.SysRoleService;
import com.ispogsecbob.modules.sys.service.SysUserRoleService;
import com.ispogsecbob.modules.sys.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * 系统用户
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年9月18日 上午9:46:09
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {
	@Autowired
	private SysUserRoleService sysUserRoleService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysUserService sysUserService;

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		System.out.println(new Gson().toJson(params));
		// 当前页，总条数 构造 page 对象
		// 新建分页
		int current = Integer.parseInt(params.get("page").toString());
		int size = Integer.parseInt(params.get("limit").toString());
		Integer startPage = 0 + size * (current - 1);
		Integer endPage = size;
		params.put("startPage", startPage);
		params.put("endPage", endPage);
		Page<SysUserEntity> page = new Page(current,size);
		List<SysUserEntity> userList = sysUserService.queryUser(params);
		int total = sysUserService.queryCount(params);
		page.setRecords(userList);
		page.setTotal(total);

		return new PageUtils(page);
	}

	@Override
	public List<String> queryAllPerms(Long userId) {
		return baseMapper.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return baseMapper.queryAllMenuId(userId);
	}

	@Override
	public SysUserEntity queryByUserName(String username) {
		return baseMapper.queryByUserName(username);
	}

	@Override
	public List<SysUserEntity> queryByUserMobile(String mobile) {
		return baseMapper.queryByUserMobile(mobile);
	}

	@Override
	public List<SysUserEntity> queryByUser() {
		return baseMapper.queryByUser();
	}

	@Override
	public List<SysUserEntity> queryUser(Map<String, Object> params) {
		return baseMapper.queryUser(params);
	}

	@Override
	public int queryCount(Map<String, Object> params) {
		return baseMapper.queryCount(params);
	}

	@Override
	@Transactional
	public void save(SysUserEntity user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		this.insert(user);

		//检查角色是否越权
		checkRole(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void update(SysUserEntity user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		this.updateById(user);

		//检查角色是否越权
		checkRole(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
	}

	@Override
	public void deleteBatch(Long[] userId) {
		this.deleteBatchIds(Arrays.asList(userId));
	}

	@Override
	public boolean updatePassword(Long userId, String password, String newPassword) {
		SysUserEntity userEntity = new SysUserEntity();
		userEntity.setPassword(newPassword);
		return this.update(userEntity,
				new EntityWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
	}

	@Override
	public boolean updateState(Long userId, Integer status) {
		return baseMapper.updateState(userId, status);
	}



	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUserEntity user){
		if(user.getRoleIdList() == null || user.getRoleIdList().size() == 0){
			return;
		}
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}

		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());

		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}
}
