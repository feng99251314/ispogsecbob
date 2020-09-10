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
public class CommonAttachments implements Serializable {

    private static final long serialVersionUID = 1L;

    private String status;

    private String name;

    private Integer percentage;

    private Integer size;

    private Long uid;

    private CommonFile response;

    private String url;

}
