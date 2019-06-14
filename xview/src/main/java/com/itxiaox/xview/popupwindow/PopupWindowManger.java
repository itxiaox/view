package com.itxiaox.xview.popupwindow;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;

import com.itxiaox.xview.adapter.CommonAdapter;
import com.itxiaox.xview.adapter.ViewHolder;
import com.itxiaox.xview.R;

import java.util.List;

public class PopupWindowManger {

    public interface PopupWindowListener {
         void onDismiss(PopupWindow popupWindow);

         void onItemClick(PopupWindow popupWindow, AdapterView<?> parent,
                                View view, int position, long id);
    }

    /**
     * 带箭头的下拉菜单
     */
    public static PopupWindow showPopupWindow(Context context,
                                                    List<String> datas, final PopupWindowListener popupWindowListener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        // 引入窗口配置文件
        View view = inflater.inflate(R.layout.xx_popupwindow_spinner, null);
        ListView listView = (ListView) view
                .findViewById(R.id.list_popupwindowstyle1);
        // 创建PopupWindow对象
        final PopupWindow popupWindow = new PopupWindow(view,
                dp2px(context, 100), LayoutParams.WRAP_CONTENT,
                false);
        // 需要设置一下此参数，点击外边可消失
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        popupWindow.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        popupWindow.setFocusable(true);
        // 设置pop消失监听
        popupWindow.setOnDismissListener(() -> {
            if (popupWindowListener != null) {
                popupWindowListener.onDismiss(popupWindow);
            }
        });
        CommonAdapter<String> adapter = new CommonAdapter<String>(context,
                datas, R.layout.xx_item_popupwindowstyle1) {

            @Override
            public void convert(ViewHolder holder, String t) {
                holder.setText(R.id.tv_popupwindowstyle1_item, t);
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((arg0, arg1, arg2, arg3) -> {
            if (popupWindowListener != null) {
                popupWindowListener.onItemClick(popupWindow, arg0, arg1,
                        arg2, arg3);
            }
        });
        return popupWindow;
    }

    /**
     * dp 转 px
     *
     * @param dpVal dp单位
     * @return
     */
    private static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
