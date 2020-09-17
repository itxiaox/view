package com.github.itxiaox.menu.popupwindow;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.widget.PopupMenu;

import com.github.itxiaox.menu.R;


@SuppressLint("RestrictedApi")
public class IconPopupMenu extends PopupMenu {
    private MenuPopupHelper helper;

    public IconPopupMenu(Context context, View anchor) {
        super(context, anchor);
        init(context, anchor, Gravity.NO_GRAVITY);
    }

    public IconPopupMenu(Context context, View anchor, int gravity) {
        super(context, anchor, gravity);
        init(context, anchor, gravity);
    }

    public IconPopupMenu(Context context, View anchor, int gravity, int popupStyleAttr, int popupStyleRes) {
        super(context, anchor, gravity, popupStyleAttr, popupStyleRes);
        init(context, anchor, gravity);
    }

    private void init(Context context, View anchor, int gravity) {
        ContextThemeWrapper wrapper =  new ContextThemeWrapper(context, R.style.IconPopupMenu);
        helper = new MenuPopupHelper(wrapper, (MenuBuilder)getMenu(), anchor);
        helper.setGravity(gravity);
    }

    @Override
    public void show() {
        helper.setForceShowIcon(true);
        helper.setGravity(getGravity());
        helper.show();
    }
}
