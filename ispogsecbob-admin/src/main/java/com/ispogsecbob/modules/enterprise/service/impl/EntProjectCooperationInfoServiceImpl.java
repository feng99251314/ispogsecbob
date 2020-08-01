package com.ispogsecbob.modules.enterprise.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ispogsecbob.common.annotation.LimitPage;
import com.ispogsecbob.common.utils.PageUtils;
import com.ispogsecbob.common.utils.Query;
import com.ispogsecbob.common.utils.R;
import com.ispogsecbob.modules.enterprise.annotation.DefaultArrayValue;
import com.ispogsecbob.modules.enterprise.annotation.DefaultValue;
import com.ispogsecbob.modules.enterprise.dao.EntProjectCooperationInfoDao;
import com.ispogsecbob.common.enums.DefValueEnum;
import com.ispogsecbob.modules.enterprise.entity.*;
import com.ispogsecbob.modules.enterprise.service.*;
import com.ispogsecbob.modules.sys.entity.SysUserEntity;
import com.ispogsecbob.modules.sys.service.SysUserRoleService;
import com.ispogsecbob.modules.sys.service.SysUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("entProjectCooperationInfoService")
public class EntProjectCooperationInfoServiceImpl extends ServiceImpl<EntProjectCooperationInfoDao, EntProjectCooperationInfoEntity> implements EntProjectCooperationInfoService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Autowired
    private UserTeacherInfoService userTeacherInfoService;

    @Autowired
    private UserPerInfoService userPerInfoService;

    @Autowired
    private EntEnterpriseInfoService entEnterpriseInfoService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private EntProjectInfoService entProjectInfoService;

    @Autowired
    private EntProjectAttachService entProjectAttachService;

    @Autowired
    private EntCoopeationAttachService entCoopeationAttachService;

    @Autowired
    private EntPersonCooperationInfoService entPersonCooperationInfoService;

    @Autowired
    private EntProjectCooperationInfoService entProjectCooperationInfoService;

    @LimitPage(targetType = java.util.Map.class, name = "项目合作分页", index = 0, pageSize = 10,  currPage = 1)
    @DefaultArrayValue(targetType = java.util.Map.class, index = 0, key = {"inApply", "inType"}, defValue = {"0", "userPerId"}, defValueEnum = {DefValueEnum.STRING, DefValueEnum.STRING})
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Integer pageSize = (Integer) params.get("pageSize");
        Integer currPage = (Integer) params.get("currPage");
        //params.put("currPage", startPage);
        //Page<?> objects = PagingTool.handlerPage(params);
        //String type = (String) params.get("inType");
        List<EntProjectCooperationInfoEntity> list = baseMapper.queryProjectCooperationInfoList(params);
        // PageUtils page = PagingTool.page(list, objects);
        return new PageUtils(list, baseMapper.queryCountPage(params), pageSize, currPage);
    }

    @DefaultArrayValue(targetType = java.util.Map.class, index = 0, key = {"inApply", "inType"}, defValue = {"0", "userPerId"}, defValueEnum = {DefValueEnum.STRING, DefValueEnum.STRING})
    @Override
    public R queryProjectCooperationInfo(Map<String, Object> params) {
        String type = (String) params.get("inType");
        SysUserEntity sysUserEntity = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        if("userPerId".equals(type)){ // 学生
            //userPerInfoService.queryUserPerIdByUserId(sysUserEntity.get)
            EntProjectCooperationInfoEntity entity = baseMapper.queryProjectCooperationInfoListForPer(params);
            SysUserEntity userEntity = sysUserService.selectById(entity.getUserPersonInfo().getUserId());
            entity.setSysUserEntity(userEntity);
            //项目信息附件
            EntityWrapper<EntProjectAttachEntity> wrapperAttach = new EntityWrapper<EntProjectAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntProjectAttachEntity> attachments = entProjectAttachService.selectList(wrapperAttach);
            entity.setEntProjectAttachEntities(attachments);
            //项目合作附件
            EntityWrapper<EntCoopeationAttachEntity> cooAttach = new EntityWrapper<EntCoopeationAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntCoopeationAttachEntity> cooAttachments = entCoopeationAttachService.selectList(cooAttach);
            entity.setEntCoopeationAttachEntities(cooAttachments);
            return R.ok().put("data", entity);
        }else if("userTeacherId".equals(type)){ // 教师
            EntProjectCooperationInfoEntity entity = baseMapper.queryProjectCooperationInfoListForTeacher(params);
            SysUserEntity userEntity = sysUserService.selectById(entity.getUserTeacherInfo().getUserId());
            entity.setSysUserEntity(userEntity);
            //项目附件
            EntityWrapper<EntProjectAttachEntity> wrapperAttach = new EntityWrapper<EntProjectAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntProjectAttachEntity> attachments = entProjectAttachService.selectList(wrapperAttach);
            entity.setEntProjectAttachEntities(attachments);
            //项目合作附件
            EntityWrapper<EntCoopeationAttachEntity> cooAttach = new EntityWrapper<EntCoopeationAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntCoopeationAttachEntity> cooAttachments = entCoopeationAttachService.selectList(cooAttach);
            entity.setEntCoopeationAttachEntities(cooAttachments);
            return R.ok().put("data", entity);
        }else if ("entInfoId".equals(type)){ // 企业
            EntProjectCooperationInfoEntity entity = baseMapper.queryProjectCooperationInfoListForEnt(params);
            SysUserEntity userEntity = sysUserService.selectById(entity.getEntEnterpriseInfo().getUserId());
            entity.setSysUserEntity(userEntity);
            //项目附件
            EntityWrapper<EntProjectAttachEntity> wrapperAttach = new EntityWrapper<EntProjectAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntProjectAttachEntity> attachments = entProjectAttachService.selectList(wrapperAttach);
            entity.setEntProjectAttachEntities(attachments);
            //项目合作附件
            EntityWrapper<EntCoopeationAttachEntity> cooAttach = new EntityWrapper<EntCoopeationAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntCoopeationAttachEntity> cooAttachments = entCoopeationAttachService.selectList(cooAttach);
            entity.setEntCoopeationAttachEntities(cooAttachments);
            return R.ok().put("data", entity);
        }
        return R.error();
    }

    @DefaultArrayValue(targetType = java.util.Map.class, index = 0, key = {"inApply", "inType"}, defValue = {"0", "userPerId"}, defValueEnum = {DefValueEnum.STRING, DefValueEnum.STRING})
    @Override
    public R queryProjectCooperationInfoNoType(Map<String, Object> params) {
        String id = String.valueOf(params.get("proCooperationInfoId"));
        EntProjectCooperationInfoEntity entProjectCooperationInfoEntity = entProjectCooperationInfoService.selectById(id);
        if(entProjectCooperationInfoEntity.getUserPerId() != null && !"".equals(entProjectCooperationInfoEntity.getUserPerId())){
            EntProjectCooperationInfoEntity entity = baseMapper.queryProjectCooperationInfoListForPer(params);
            SysUserEntity userEntity = sysUserService.selectById(entity.getUserPersonInfo().getUserId());
            entity.setSysUserEntity(userEntity);
            //项目信息附件
            EntityWrapper<EntProjectAttachEntity> wrapperAttach = new EntityWrapper<EntProjectAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntProjectAttachEntity> attachments = entProjectAttachService.selectList(wrapperAttach);
            entity.setEntProjectAttachEntities(attachments);
            //项目合作附件
            EntityWrapper<EntCoopeationAttachEntity> cooAttach = new EntityWrapper<EntCoopeationAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntCoopeationAttachEntity> cooAttachments = entCoopeationAttachService.selectList(cooAttach);
            entity.setEntCoopeationAttachEntities(cooAttachments);
            return R.ok().put("data", entity);
        }
        if(entProjectCooperationInfoEntity.getUserTeacherId() != null && !"".equals(entProjectCooperationInfoEntity.getUserTeacherId())){
            EntProjectCooperationInfoEntity entity = baseMapper.queryProjectCooperationInfoListForTeacher(params);
            SysUserEntity userEntity = sysUserService.selectById(entity.getUserTeacherInfo().getUserId());
            entity.setSysUserEntity(userEntity);
            //项目附件
            EntityWrapper<EntProjectAttachEntity> wrapperAttach = new EntityWrapper<EntProjectAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntProjectAttachEntity> attachments = entProjectAttachService.selectList(wrapperAttach);
            entity.setEntProjectAttachEntities(attachments);
            //项目合作附件
            EntityWrapper<EntCoopeationAttachEntity> cooAttach = new EntityWrapper<EntCoopeationAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntCoopeationAttachEntity> cooAttachments = entCoopeationAttachService.selectList(cooAttach);
            entity.setEntCoopeationAttachEntities(cooAttachments);
            return R.ok().put("data", entity);
        }
        if(entProjectCooperationInfoEntity.getEntInfoId() != null && !"".equals(entProjectCooperationInfoEntity.getEntInfoId())){
            EntProjectCooperationInfoEntity entity = baseMapper.queryProjectCooperationInfoListForEnt(params);
            SysUserEntity userEntity = sysUserService.selectById(entity.getEntEnterpriseInfo().getUserId());
            entity.setSysUserEntity(userEntity);
            //项目附件
            EntityWrapper<EntProjectAttachEntity> wrapperAttach = new EntityWrapper<EntProjectAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntProjectAttachEntity> attachments = entProjectAttachService.selectList(wrapperAttach);
            entity.setEntProjectAttachEntities(attachments);
            //项目合作附件
            EntityWrapper<EntCoopeationAttachEntity> cooAttach = new EntityWrapper<EntCoopeationAttachEntity>();
            wrapperAttach.eq("pro_info_id", entity.getProInfoId());
            List<EntCoopeationAttachEntity> cooAttachments = entCoopeationAttachService.selectList(cooAttach);
            entity.setEntCoopeationAttachEntities(cooAttachments);
            return R.ok().put("data", entity);
        }
        return R.error();
    }

    @DefaultValue(targetType = java.util.Map.class, index = 0, key = "inApply", defValue = "1", defValueEnum = DefValueEnum.STRING)
    @Override
    public R updateProjectExamine(Map<String, Object> params) {
        if(params.get("inApply") != null && !"0".equals(params.get("inApply"))){
            baseMapper.updateProjectExamine( params);
        } else if((params.get("inApply") != null && "0".equals(params.get("inApply")))){
            String id = String.valueOf(params.get("proCooperationInfoId"));
            baseMapper.deleteById(Long.valueOf(id));
        }
        return R.ok();
    }

    @Override
    public EntProjectCooperationInfoEntity queryEntProjectCooperationInfoByProjectId(Long projectId) {
        return baseMapper.queryEntProjectCooperationInfoByProjectId(projectId);
    }

    @Override
    public R insertProjectCooperation(EntProjectCooperationInfoEntity entProjectCooperationInfo) {
        HashMap<Long, Long> roleMap = new HashMap<>();
        roleMap.put(11L, 11L);
        roleMap.put(12L, 12L);
        roleMap.put(7L, 7L);
        roleMap.put(2L, 2L);
        roleMap.put(3L, 3L);
        SysUserEntity user = (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
        if(user == null){
            return  R.error("未登录系统或已过期，请重新登录。");
        }
        List<Long> roles = sysUserRoleService.queryRoleIdList(user.getUserId());
        if(roles != null && roles.size() > 0){
            for(int i = 0; i < roles.size(); i++){
                Long aLong = roles.get(i);
                Long aLong1 = roleMap.get(aLong);
                if(aLong1 != null && aLong1 == 11L || aLong1 == 2L){ // 学生
                    Long userPerId = userPerInfoService.queryUserPerIdByUserId(user.getUserId());
                    entProjectCooperationInfo.setUserPerId(userPerId);
                    break;
                }else if (aLong1 != null && aLong1 == 12L  || aLong1 == 3L){ // 教师
                    Long userTeacherId = userTeacherInfoService.queryUserTeacherIdByUserId(user.getUserId());
                    entProjectCooperationInfo.setUserTeacherId(userTeacherId);
                    break;
                }else if (aLong1 != null && aLong1 == 7L){ // 企业
                    Long entInfoId = entEnterpriseInfoService.queryEntInfoIdByUserId(user.getUserId());
                    entProjectCooperationInfo.setEntInfoId(entInfoId);
                    break;
                }
            }
            baseMapper.insert(entProjectCooperationInfo);
        }
        return R.ok();
    }

    @Override
    public List<Long> queryProjectInfoIdByType(String type, long id) {
        return baseMapper.queryProjectInfoIdByType(type, id);
    }

    @Override
    public List<Long> queryProInfoIdsByInApply(String inApply) {
        return baseMapper.queryProInfoIdsByInApply(inApply);
    }

    @Override
    public ArrayList<Long> queryProInfoIdsByProInfoId(ArrayList<Long> projectIds) {
        return baseMapper.queryProInfoIdsByProInfoId(projectIds);
    }

    @Override
    public boolean deleteByProInfoId(Long proInfoId) {
        return baseMapper.deleteByProInfoId(proInfoId);
    }

    @DefaultArrayValue(targetType = java.util.Map.class, index = 0, key = {"inApply", "inType"}, defValue = {"0", "userPerId"}, defValueEnum = {DefValueEnum.STRING, DefValueEnum.STRING})
    @Override
    public R queryProjectPage(Map<String, Object> params, Long userId) {
        String type = (String) params.get("inType");
        EntityWrapper<EntProjectCooperationInfoEntity> wrapper = new EntityWrapper<>();
        wrapper.eq("in_apply", params.get("inApply"));
        if("userPerId".equals(type)){
            EntUserPersonInfoEntity entUserPersonInfoEntity = userPerInfoService.selectOne(new EntityWrapper<EntUserPersonInfoEntity>().eq("user_id", userId));
            wrapper.eq("user_per_id", entUserPersonInfoEntity.getUserPerId());
        }else if("userTeacherId".equals(type)){
            EntUserTeacherInfoEntity teacherInfoEntity = userTeacherInfoService.selectOne(new EntityWrapper<EntUserTeacherInfoEntity>().eq("user_id", userId));
            wrapper.eq("user_teacher_id", teacherInfoEntity.getUserTeacherId());
        }else if("entInfoId".equals(type)){
            EntEnterpriseInfoEntity entEnterpriseInfoEntity = entEnterpriseInfoService.selectOne(new EntityWrapper<EntEnterpriseInfoEntity>().eq("user_id", userId));
            wrapper.eq("ent_info_id", entEnterpriseInfoEntity.getEntInfoId());
        }
        Page<EntProjectCooperationInfoEntity> page = this.selectPage(new Query<EntProjectCooperationInfoEntity>(params).getPage(), wrapper);
        if(page.getRecords().size() > 0){
            for (int i = 0; i < page.getRecords().size(); i++) {
                EntProjectInfoEntity entProjectInfoEntity = entProjectInfoService.selectById(page.getRecords().get(i).getProInfoId());
                page.getRecords().get(i).setProjectInfo(entProjectInfoEntity);
            }
        }
        return R.ok().put("page", new PageUtils(page));
    }

    @DefaultArrayValue(targetType = java.util.Map.class, index = 0, key = {"inApply", "inType"}, defValue = {"0", "userPerId"}, defValueEnum = {DefValueEnum.STRING, DefValueEnum.STRING})
    @Override
    public R queryCooperationByApplyPage(Map<String, Object> params, Long userId) {
        String type = (String) params.get("inType");
        EntityWrapper<EntProjectCooperationInfoEntity> wrapper = new EntityWrapper<>();
        //查询条件
        if(params.get("key") != null && !"".equals(params.get("key"))){
            EntProjectInfoEntity entProjectInfoEntity = entProjectInfoService.selectOne(new EntityWrapper<EntProjectInfoEntity>().eq("pro_name", params.get("key")));
            wrapper.eq("pro_info_id", entProjectInfoEntity.getProInfoId());
        }
        if("userPerId".equals(type)){
            EntUserPersonInfoEntity entUserPersonInfoEntity = userPerInfoService.selectOne(new EntityWrapper<EntUserPersonInfoEntity>().eq("user_id", userId));
            wrapper.eq("user_per_id", entUserPersonInfoEntity.getUserPerId());
        }else if("userTeacherId".equals(type)){
            EntUserTeacherInfoEntity teacherInfoEntity = userTeacherInfoService.selectOne(new EntityWrapper<EntUserTeacherInfoEntity>().eq("user_id", userId));
            wrapper.eq("user_teacher_id", teacherInfoEntity.getUserTeacherId());
        }else if("entInfoId".equals(type)){
            EntEnterpriseInfoEntity entEnterpriseInfoEntity = entEnterpriseInfoService.selectOne(new EntityWrapper<EntEnterpriseInfoEntity>().eq("user_id", userId));
            wrapper.eq("ent_info_id", entEnterpriseInfoEntity.getEntInfoId());
        }
        Page<EntProjectCooperationInfoEntity> page = this.selectPage(new Query<EntProjectCooperationInfoEntity>(params).getPage(), wrapper);
        if(page.getRecords().size() > 0){
            // 查询已审核之后的合作状态，包含1,2,3
            for (int i = 0; i < page.getRecords().size(); i++) {
                if(params.get("inApply").equals("0")){
                    if(!page.getRecords().get(i).getInApply().equals("0")){
                        page.getRecords().remove(i);
                        --i;
                    }
                }
                if(!params.get("inApply").equals("0")){
                    if(page.getRecords().get(i).getInApply().equals("0")){
                        page.getRecords().remove(i);
                        --i;
                    }
                }

            }
            for (int i = 0; i < page.getRecords().size(); i++) {
                EntProjectInfoEntity entProjectInfoEntity = entProjectInfoService.selectById(page.getRecords().get(i).getProInfoId());
                page.getRecords().get(i).setProjectInfo(entProjectInfoEntity);
            }
        }
        return R.ok().put("page", new PageUtils(page));
    }



    // ================ 放弃列表方法，请勿删除 ====================
//    private void template(){
//        EntityWrapper<EntProjectCooperationInfoEntity> wrapper = new EntityWrapper<>();
//        String type = (String) params.get("inType");
//        if("userPerId".equals(type)){ // 学生
//            wrapper.isNotNull("user_per_id");
//        }else if("userTeacherId".equals(type)){ // 教师
//            wrapper.isNotNull("user_teacher_id");
//        }else if ("entInfoId".equals(type)){ // 企业
//            wrapper.isNotNull("ent_info_id");
//        }
//        if("0".equals(params.get("inApply"))){
//            wrapper.eq("in_apply", "0");
//        }else {
//            wrapper.eq("in_apply", "1");
//        }
//        if(params.get("key") != null){
//            wrapper.like("pro_name", (String) params.get("key"));
//        }
//        Page<EntProjectCooperationInfoEntity> page = this.selectPage(new Query<EntProjectCooperationInfoEntity>(params).getPage(),wrapper);
//        List<EntProjectCooperationInfoEntity> records = page.getRecords();
//        if(records != null && records.size() > 0){
//            for(int i = 0; i<records.size(); i++) {
//                EntProjectCooperationInfoEntity entity = records.get(i);
//                EntEnterpriseInfoEntity ent = entEnterpriseInfoService.selectById(entity.getEntInfoId());
//                entity.setEntEnterpriseInfo(ent);
//            }
//        }
//        return new PageUtils(page);
//    }

}
