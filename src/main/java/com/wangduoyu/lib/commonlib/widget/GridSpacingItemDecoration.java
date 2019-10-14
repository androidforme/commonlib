package com.wangduoyu.lib.commonlib.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * GridLayoutManager（网格布局）设置item的间隔
 *
 * 作者： 周旭 on 2017年7月20日 0020.
 * 邮箱：374952705@qq.com
 * 博客：http://www.jianshu.com/u/56db5d78044d
 *
 *  调用的地方
 *  int spanCount = 3; // 3 columns
 *         int spacing = 50; // 50px
 *         boolean includeEdge = false;
 *         mRecyclerView.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
 *
 *
 *         如果你了别人的RecycleView 上拉加载下拉刷新，addItemDecoration 这个方法 不一定会给你加上，你可以在源码上自行添加。
 *
 *         public void addItemDecoration(RecyclerView.ItemDecoration decor) {
 *     mRecyclerView.addItemDecoration(decor,-1);
 * }
 */

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanCount; //列数
    private int spacing; //间隔
    private boolean includeEdge; //是否包含边缘

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        //这里是关键，需要根据你有几列来判断
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // item bottom
        } else {
            outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing; // item top
            }
        }
    }
}
