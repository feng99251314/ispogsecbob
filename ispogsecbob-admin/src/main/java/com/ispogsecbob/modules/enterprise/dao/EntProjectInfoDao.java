package com.ispogsecbob.modules.enterprise.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ispogsecbob.modules.enterprise.entity.EntProjectInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
/**
 * 项目信息表
 * 
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@Mapper
public interface EntProjectInfoDao extends BaseMapper<EntProjectInfoEntity> {

    /**
     * 根据项目id和项目类型 查询项目信息列表 学生
     * @param params
     * @return
     */
    List<EntProjectInfoEntity> queryEntProjectInfoListByIdPerId(Map<String, Object> params);

    /**
     * 根据项目id和项目类型 查询项目信息列表 教师
     * @param params
     * @return
     */
    List<EntProjectInfoEntity> queryEntProjectInfoListByIdTeacherId(Map<String, Object> params);

    /**
     * 根据项目id和项目类型 查询项目信息列表 企业
     * @param params
     * @return
     */
    List<EntProjectInfoEntity> queryEntProjectInfoListByIdEntId(Map<String, Object> params);

    /**
     * 根据项目id和项目类型 查询项目信息 学生
     * @param id
     * @return
     */
    EntProjectInfoEntity queryEntProjectInfoByIdPerId(Long id);

    /**
     * 根据项目id和项目类型 查询项目信息 教师
     * @param id
     * @return
     */
    EntProjectInfoEntity queryEntProjectInfoByIdTeacherId(Long id);

    /**
     * 根据项目id和项目类型 查询项目信息 企业
     * @param id
     * @return
     */
    EntProjectInfoEntity queryEntProjectInfoByIdEntId(Long id);

    /**
     * 处理项目信息审核
     * @param params
     * @return
     */
    boolean updateEntExamine(Map<String, Object> params);


    /**
     * 学生用户项目
     * @param userPerId
     * @return
     */
    List<EntProjectInfoEntity> queryProjectsByUserPerId(Long userPerId);

    /**
     * 教师用户项目
     * @param userTeacherId
     * @return
     */
    List<EntProjectInfoEntity> queryProjectsByUserTeacherId(Long userTeacherId);

    /**
     * 企业用户项目
     * @param entInfoId
     * @return
     */
    List<EntProjectInfoEntity> queryProjectsByEnterId(Long entInfoId);

    /**
     * 查询项目
     * @param proId
     * @param inApply
     * @return
     */
    EntProjectInfoEntity queryProjectByProjectIdAndInApply(@Param("proInfoId") Long proId, @Param("inApply") String inApply);

}
