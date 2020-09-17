package com.itxiaox.xview.java;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.github.itxiaox.dialog.AlertDialog;
import com.github.itxiaox.dialog.AlertInputDialog;
import com.github.itxiaox.dialog.CommomDialog;
import com.github.itxiaox.dialog.CommonDialogFragment;
import com.github.itxiaox.ios.ActionSheetDialog;
import com.itxiaox.android.xutils.ui.DensityUtils;
import com.itxiaox.android.xutils.ui.UIHelper;
import com.itxiaox.xview.R;

public class DialogActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        findViewById(R.id.btn_common).setOnClickListener(v -> commonDialog());
        findViewById(R.id.btn_alert).setOnClickListener(v -> alertDialog());
        findViewById(R.id.btn_input).setOnClickListener(v -> inputDialog());
        findViewById(R.id.btn_dialogFragment).setOnClickListener(v -> dialogFragment());
        findViewById(R.id.btn_actionsheet).setOnClickListener(v -> actionSheet());
    }

    private void dialogFragment() {
        CommonDialogFragment commonDialogFragment = new CommonDialogFragment();
        commonDialogFragment.setOnCloseListener((dialog, confirm) -> {
            dialog.dismiss();
            if (confirm){
                UIHelper.toast("点击了确定");
            }else {
                UIHelper.toast("点击了取消");
            }
        });
        //设置返回键
        commonDialogFragment.setOnKeyListener((dialog, keyCode, event) -> false);
        commonDialogFragment.setTitle("设置标题")
        .setContent("设置内容")
        .setNegativeButton("取消")
        .setPositiveButton("确定");
        commonDialogFragment.show(getSupportFragmentManager(), "通用的DialogFragment");

    }

    private void commonDialog() {
        CommomDialog commomDialog = new CommomDialog(this, (dialog, confirm) -> {
            dialog.dismiss();
            if (confirm){//确定
                UIHelper.toast(DialogActivity.this,"点击了确定");
            }else {//取消
                UIHelper.toast(DialogActivity.this,"点击了取消");
            }

        }).setContent("我是通用对话框")
                .setNegativeButton("取消")
                .setPositiveButton("确定")
                .setTitle("标题");
        commomDialog.show();
    }

    private void alertDialog(){
            new AlertDialog(DialogActivity.this,
                    DensityUtils.dip2px(250),
                    DensityUtils.dip2px(250))
                    .builder()
                    .setTitle("对话框标题")
                    .setMsg("对话框内容")
                    .setNegativeButton("按钮一", view -> UIHelper.toast("您点击了 按钮一 "))
                    .setPositiveButton("按钮二", view -> UIHelper.toast("点击了按钮二"))
                    .show();
    }

    private void inputDialog(){
        new AlertInputDialog(DialogActivity.this)
                .builder()
                .setTitle("标题")
                .setMsg("消息内容")
                .setNegativeButton("确定", view -> {
                    UIHelper.toast("您点击了确定");
                })
                .setPositiveButton("取消", view -> {
                    UIHelper.toast("您点击了取消");
                })
                .show();
    }

    private void actionSheet(){
        ActionSheetDialog actionSheetDialog = new ActionSheetDialog(this)
                .builder()
                .addSheetItem("item1",
                        ActionSheetDialog.SheetItemColor.Blue, which -> UIHelper.toast("item1 被点击了"))
                .addSheetItem("item2", ActionSheetDialog.SheetItemColor.Red, which -> {
                    UIHelper.toast("item2 被点击了");
                })
                .addSheetItem("item3", ActionSheetDialog.SheetItemColor.Black, which -> UIHelper.toast("item3 被点击了"))
                .setTitle("设置标题");
        actionSheetDialog.show();

    }
}