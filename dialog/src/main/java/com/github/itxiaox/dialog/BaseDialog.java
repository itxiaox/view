package com.github.itxiaox.dialog;

import android.content.Context;

/**
 * Author:xiao
 * Time: 2020/7/16 20:58
 * Description:This is BaseDialog
 */
public class BaseDialog {
    protected int width;
    protected int height;
    protected Context context;
    /**
     * 创建对话框
     * @param context 上下文对象
     */
    public BaseDialog(Context context) {
        this.context = context;
    }
    public BaseDialog(Context context, int width, int height) {
        this.context = context;
        this.width = width;
        this.height = height;
    }
}
