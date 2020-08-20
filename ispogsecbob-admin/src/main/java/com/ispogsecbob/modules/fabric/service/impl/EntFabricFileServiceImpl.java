package com.ispogsecbob.modules.fabric.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ispogsecbob.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.Query;

import com.ispogsecbob.modules.fabric.dao.EntFabricFileDao;
import com.ispogsecbob.modules.fabric.entity.EntFabricFileEntity;
import com.ispogsecbob.modules.fabric.service.EntFabricFileService;


@Service("entFabricFileService")
public class EntFabricFileServiceImpl extends ServiceImpl<EntFabricFileDao, EntFabricFileEntity> implements EntFabricFileService {

    /**
     * 系统用户
     */
    @Autowired
    private SysUserService sysUserService;

    /**
     * 获取列表
     * @param params
     * @return
     */
    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        EntityWrapper<EntFabricFileEntity> ew = new EntityWrapper<>();

        if (params.get("key")!=null)ew.like("file_name",params.get("key").toString());
        //查询自己
        if ("1".equals(params.get("hasApply")))ew.eq("user_id",params.get("userId"));
        //查询可以审批
        if ("2".equals(params.get("hasApply")))ew.where("user_id != {0}",params.get("userId"));

        Page<EntFabricFileEntity> page = this.selectPage(
                new Query<EntFabricFileEntity>(params).getPage(),
                ew
        );

        List<EntFabricFileEntity> records = page.getRecords();

        records.forEach(v->{
            v.setSysUserEntity(sysUserService.selectById(v.getUserId()));
        });

        return new PageUtils(page);
    }

    /**
     * 通过文件hash查询
     * @param sha_256
     * @return
     */
    @Override
    public EntFabricFileEntity findBySHA256(String sha_256) {

        EntityWrapper<EntFabricFileEntity> ew = new EntityWrapper<>();

        ew.eq("file_hash",sha_256);

        EntFabricFileEntity entFabricFileEntity = super.selectOne(ew);

        if (entFabricFileEntity!=null)entFabricFileEntity.setSysUserEntity(sysUserService.selectById(entFabricFileEntity.getUserId()));

        return entFabricFileEntity;
    }

}