package com.ispogsecbob.common.utils;

import java.util.HashMap;

/**
 * @author: Mikey
 * Email：biaogejiushibiao@outlook.com
 * Date: 2018/11/24
 **/
public class StringMapUtils extends HashMap<String, Object> {
    private StringUtils stringUtils = new StringUtils();

    @Override
    public StringMapUtils put(String key, Object value) {
        key = stringUtils.findProperty(key, true);
        super.put(key, value);
        return this;
    }
}
