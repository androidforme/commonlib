package com.wangduoyu.lib.commonlib.base.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.wangduoyu.lib.commonlib.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 可以添加头部的适配器 ，仅支持databing，做的太全反而代码臃肿；适合项目就好；
 *
 * @param <T>
 */
public abstract class BaseHeaderAdapter<T> extends RecyclerView.Adapter<BaseHeaderAdapter.BindingViewHolder> {
    protected  final String TAG = this.getClass().getSimpleName();

    public static final int HEADER_VIEW = 0x00000111;
    protected int mLayoutResId;
    protected List<T> mData;

    private LinearLayout mHeaderLayout;

    // 应用需要复写这个方法
    protected abstract void convert(BindingViewHolder holder, T t);

    public BaseHeaderAdapter(@LayoutRes int layoutResId, List<T> data) {
        this.mData = data == null ? new ArrayList<T>() : data;
        if (layoutResId != 0) {
            this.mLayoutResId = layoutResId;
        }
    }

    public BaseHeaderAdapter(@LayoutRes int layoutResId) {
        this(layoutResId, null);
    }

    @NonNull
    @Override
    public BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BindingViewHolder h;
        if (viewType == HEADER_VIEW) {
            h = new BindingViewHolder(new ViewAbstract(mHeaderLayout));
        } else {
            h = new BindingViewHolder(inflate(parent));
        }
        return h;
    }

    private ViewAbstract inflate(ViewGroup parent) {
        return new ViewAbstract(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), mLayoutResId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BindingViewHolder holder, int position) {
        int viewType = holder.getItemViewType();
        LogUtils.d( this.toString() + " position = " + position + " viewType = " + viewType);
        if (viewType == HEADER_VIEW) { // 不处理
        } else {
            convert(holder, mData.get(position - getHeaderLayoutCount()));
        }
    }

    @Override
    public int getItemViewType(int position) {

        int numHeaders = getHeaderLayoutCount();

        if (position < numHeaders) {
            return HEADER_VIEW;
        }
        return super.getItemViewType(position);
    }

    /**
     * 添加头部view
     *
     * @param view
     */
    public void addHeaderView(View view) {
        if (mHeaderLayout == null) {
            mHeaderLayout = new LinearLayout(view.getContext());
            mHeaderLayout.setOrientation(LinearLayout.VERTICAL);
            mHeaderLayout.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        }
        mHeaderLayout.addView(view);
        if (mHeaderLayout.getChildCount() == 1) {
            notifyItemChanged(0);
        }
    }

    public List<T> getData() {
        return mData;
    }

    public void setNewData(List<T> data) {
        LogUtils.d(this.toString() + " size = " + data.size());
        this.mData = data == null ? new ArrayList<T>() : data;
        LogUtils.d(this.toString() + " mData = " + data.size());
        notifyDataSetChanged();
    }

    public void addData(@NonNull T data) {
        mData.add(data);
        notifyItemChanged(mData.size() + getHeaderLayoutCount());
    }

    public void addData(@IntRange(from = 0) int position, @NonNull T data) {
        mData.add(position, data);
        notifyItemChanged(position + getHeaderLayoutCount());
    }


    private int getHeaderLayoutCount() {
        if (mHeaderLayout == null || mHeaderLayout.getChildCount() == 0)
            return 0;
        return 1;
    }

    @Override
    public int getItemCount() {
        return getHeaderLayoutCount() + (mData == null ? 0 : mData.size());
    }

    public static class BindingViewHolder extends RecyclerView.ViewHolder {
        ViewDataBinding binding;
        View rootView;

        public BindingViewHolder(@NonNull ViewAbstract root) {
            super((root.getEntity() instanceof ViewDataBinding) ? ((ViewDataBinding) root.getEntity()).getRoot() : (View) root.getEntity());

            if (root.getEntity() instanceof ViewDataBinding) {
                this.binding = (ViewDataBinding) root.getEntity();
            } else {
                this.rootView = (View) root.getEntity();
            }
        }

        public ViewDataBinding getBinding() {
            return binding;
        }

        public View getRootView() {
            return rootView;
        }
    }
}
