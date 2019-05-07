package com.itxiaox.xview.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/5/3 0003.
 */
public class UIHelper {
    private static Context mContext;
    private static Toast toast;

    /**
     * 此方法最新调用，一般在application中进行初始化
     * @param context
     */
    public static void  init(Context context){
        mContext = context.getApplicationContext();
    }

    public static Toast showToast(String msg){
        if(toast == null) {
            toast = new Toast(mContext);
        }
        toast.setText(msg);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
        return toast;
    }
}
