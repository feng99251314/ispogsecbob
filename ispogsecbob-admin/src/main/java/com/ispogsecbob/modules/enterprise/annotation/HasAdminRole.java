package com.ispogsecbob.modules.enterprise.annotation;

import java.lang.annotation.*;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description 管理员角色判断拦截注解
 * @date 2019/10/22
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasAdminRole {

    /**
     * 目标类型
     * @return
     */
    Class<?> targetType();

    /**
     * 类型所在索引
     * @return
     */
    int index();

    /**
     * 管理员角色id
     * @return
     */
    String[] roleIds();

    /**
     * 学生角色id
     * @return
     */
    String perRoleId();

    /**
     * 学生角色键
     * @return
     */
    String perRoleKey();

    /**
     * 教师角色id
     * @return
     */
    String teacherRoleId();

    /**
     * 教师角色key
     * @return
     */
    String teacherRoleKey();

    /**
     * 企业角色id
     * @return
     */
    String entRoleId();

    /**
     * 企业角色key
     * @return
     */
    String entRoleKey();

}
