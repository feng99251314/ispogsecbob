package com.ispogsecbob.modules.enterprise.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ispogsecbob.modules.enterprise.entity.EntPatentInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 企业/教师知识产权信息表
 * 
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@Mapper
public interface EntPatentInfoDao extends BaseMapper<EntPatentInfoEntity> {
	
}
