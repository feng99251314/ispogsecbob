package com.ispogsecbob.modules.enterprise.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ispogsecbob.modules.enterprise.entity.EntRecruitmentInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.Map;
import java.util.List;

/**
 * 招聘信息表
 * 
 * @author 麦奇
 * @email biaogejiushibiao@outlook.com
 * @date 2019-09-10 22:19:50
 */
@Mapper
public interface EntRecruitmentInfoDao extends BaseMapper<EntRecruitmentInfoEntity> {

    Integer queryCountPage(Map<String, Object> params);

    /**
     * 招聘信息表 --》 id 查招聘信息 + 企业信息
     * @return
     */
    EntRecruitmentInfoEntity entRecruitmentInfoById(Long recruitmentInfoId);

    /**
     * 招聘信息表 --》 id 查招聘信息 + 企业信息 -- > 列表
     * @return
     */
    List<EntRecruitmentInfoEntity> entRecruitmentInfoList(Map<String, Object> params);

    /**
     * 更新招聘信息---审核
     * @param recruitmentInfoId
     * @param inApply
     * @return
     */
    boolean updateInApply(Long recruitmentInfoId, String inApply);

}
