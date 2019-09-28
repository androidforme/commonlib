package com.wangduoyu.lib.commonlib.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.Button;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;


public class ResourcesUtils {

    /**
     * 兼顾新旧版本的获取颜色的方法
     *
     * @param context Context
     * @param resId   颜色的资源ID
     * @return
     */
    @TargetApi(Build.VERSION_CODES.M)
    @SuppressWarnings("deprecation")
    public static int getColor(Context context, @ColorRes int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return context.getResources().getColor(resId, context.getTheme());
        } else {
            return context.getResources().getColor(resId);
        }
    }

    /**
     * 兼顾新旧版本的获取Drawable的方法
     *
     * @param context    Context
     * @param drawableID Drawable的资源ID
     * @return
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("deprecation")
    public static Drawable getDrawable(Context context, @DrawableRes int drawableID) {
        // 根据SDK版本判断调用方法
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(drawableID, context.getTheme());
        } else {
            return context.getResources().getDrawable(drawableID);
        }
    }


    /**
     * 兼顾新旧版本的给按钮设置背景的方法
     *
     * @param button     按钮
     * @param background 背景
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    public static void setBackground(Button button, Drawable background) {
        // 根据SDK版本判断调用方法
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            button.setBackgroundDrawable(background);
        } else {
            button.setBackground(background);
        }
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (spValue * scale + 0.5f);
    }
}
