package com.wangduoyu.lib.commonlib.base.recyclerview;

public class ViewAbstract {
    private Object entity;

    public ViewAbstract(Object t) {
        entity = t;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

}
