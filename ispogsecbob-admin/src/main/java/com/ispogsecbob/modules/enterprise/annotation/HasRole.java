package com.ispogsecbob.modules.enterprise.annotation;

import java.lang.annotation.*;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description aop实现角色分离
 * @date 2019/9/23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasRole {

    String name();

    String[] roles();

}
