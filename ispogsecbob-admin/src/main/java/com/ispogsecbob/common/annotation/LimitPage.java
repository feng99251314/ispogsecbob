package com.ispogsecbob.common.annotation;

import com.ispogsecbob.common.enums.DefValueEnum;
import com.ispogsecbob.common.enums.LimitPageEnumsKey;

import java.lang.annotation.*;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description 页面分页数据拦截注解
 * @date 2019/10/23
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LimitPage {

    /** 目标类型 **/
    Class<?> targetType();

    /** 当前分页名 **/
    String name() default "";

    /** 参数索引 **/
    int index();

    /** 当前分页大小 **/
    int pageSize() default 10;

    /** 当前分页大小键 **/
    String pageSizeKey() default "limit";

    String pageSizeTarKey() default "pageSize";

    /** 当前分页大小类型 **/
    DefValueEnum pageSizeEnum() default DefValueEnum.INTEGER;

    /** 当前分页页 **/
    int currPage() default 1;

    /** 当前分页页键 **/
    String pageKey() default "page";

    String currPageKey() default "currPage";

    /** 当前分页页类型 **/
    DefValueEnum currPageEnum() default DefValueEnum.INTEGER;

}
