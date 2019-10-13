package com.wangduoyu.lib.commonlib.base.repository;

import java.util.HashMap;

/**
 * 网络请求参数
 */
public class QueryParam extends HashMap<String, String> {

    public static QueryParam build() {
        return new QueryParam();
    }

    public QueryParam addParam(String key, Object value) {
        this.put(key, String.valueOf(value));
        return this;
    }
}
