package com.wangduoyu.lib.commonlib.base.recyclerview;

import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class CommonViewHolder<DB extends ViewDataBinding> extends RecyclerView.ViewHolder {

    public DB bindView;
    // 每一个item都必须持有的一个ViewDataBinding子类对象

    public CommonViewHolder(DB bindView) {
        super(bindView.getRoot());
        this.bindView = bindView;
    }
}