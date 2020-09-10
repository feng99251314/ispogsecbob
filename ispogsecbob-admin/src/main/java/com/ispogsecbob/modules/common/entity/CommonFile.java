package com.ispogsecbob.modules.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 麦奇
 * email: biaogejiushibiao@outlook.com
 * @description
 * @date 2019/9/20
 */
@Data
public class CommonFile implements Serializable {

    private static final long serialVersionUID = 1L;

    private String msg;

    private Integer code;

    private String data;

}
