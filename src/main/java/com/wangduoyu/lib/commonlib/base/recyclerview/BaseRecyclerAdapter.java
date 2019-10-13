package com.wangduoyu.lib.commonlib.base.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * dataBing
 */

public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseRecyclerAdapter.BindingViewHolder> {
    private List<T> mData;
    private OnItemClickListener mClickListener;
    private OnItemLongClickListener mLongClickListener;
    public  Context mContext ;


    public BaseRecyclerAdapter(Context context) {
        this(context,null);
    }

    public BaseRecyclerAdapter(Context context,List<T> list) {
        mData = (list != null) ? list : new ArrayList<T>();
        mContext = context;
    }

    public void setData(List<T> list) {
        mData = (list != null) ? list : new ArrayList<T>();
        notifyDataSetChanged();
    }

    @Override
    public BaseRecyclerAdapter.BindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final BaseRecyclerAdapter.BindingViewHolder holder = new BaseRecyclerAdapter.BindingViewHolder(inflate(parent,viewType));
        if (mClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mClickListener.onItemClick(holder.itemView, holder.getLayoutPosition());
                }
            });
        }
        if (mLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mLongClickListener.onItemLongClick(holder.itemView, holder.getLayoutPosition());
                    return true;
                }
            });
        }
        return holder;
    }

    private ViewAbstract inflate(ViewGroup parent, int viewType) {
        return new ViewAbstract(DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), getItemLayoutId(viewType), parent, false));
    }

    @Override
    public void onBindViewHolder(BaseRecyclerAdapter.BindingViewHolder holder, int position) {
        bindData(holder, position, mData.get(position));
    }

    public T getItem(int pos) {
        return mData.get(pos);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void add(int pos, T item) {
        mData.add(pos, item);
        notifyItemInserted(pos);
    }

    public void delete(int pos) {
        mData.remove(pos);
        notifyItemRemoved(pos);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        mLongClickListener = listener;
    }

    @SuppressWarnings("SameReturnValue")
    abstract public @LayoutRes
    int getItemLayoutId(int viewType);

    abstract public void bindData(BaseRecyclerAdapter.BindingViewHolder holder, int position, T item);

    public interface OnItemClickListener {
        void onItemClick(View itemView, int pos);
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View itemView, int pos);
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
