package com.ispogsecbob.common.enums;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description 分页拦截键的枚举类
 * @date 2019/10/23
 */
public enum LimitPageEnumsKey {

    /** 分页页面条数键 **/
    PAGESIZE("limit"),

    /** 页面键 **/
    CURRPAGE("page");

    private String value;

    LimitPageEnumsKey(String value){
        this.value = value;
    }

}
