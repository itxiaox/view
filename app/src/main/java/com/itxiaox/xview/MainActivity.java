package com.itxiaox.xview;//package com.itxiaox.xxview;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.itxiaox.address.addresspicker.AddressPicker;
import com.github.itxiaox.address.dialog.AddressDialog;
import com.github.itxiaox.dialog.AlertDialog;
import com.github.itxiaox.dialog.AlertInputDialog;
import com.github.itxiaox.menu.dropdownlist.DropListEditText;
import com.github.itxiaox.menu.dropdownlist.DropListTextView;
import com.github.itxiaox.menu.dropdownlist.DropdownList;
import com.github.itxiaox.picker.PickerUtils;
import com.itxiaox.android.xutils.ui.DensityUtils;
import com.itxiaox.xview.databinding.ActivityMainBinding;
import com.itxiaox.xview.menu.MenuActivity;
import com.itxiaox.xview.view.ViewActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        event();
    }


    private void event() {
        mainBinding.tvView.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ViewActivity.class));
        });
        mainBinding.tvProgressBar.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ProgressBarActivity.class));
        });
        mainBinding.tvMenu.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, MenuActivity.class));
        });
        mainBinding.tvDialog.setOnClickListener(view -> {
//            new AlertDialog(MainActivity.this)
            new AlertDialog(MainActivity.this,
                    DensityUtils.dip2px(250),
                    FrameLayout.LayoutParams.MATCH_PARENT)
                    .builder()
                    .setTitle("对话框标题")
                    .setMsg("对话框内容")
                    .setNegativeButton("按钮一", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).setPositiveButton("按钮二", new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            }).show();
        });
        mainBinding.tvInputDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertInputDialog(MainActivity.this)
                        .builder()
                        .setTitle("标题")
                        .setMsg("消息内容")
                        .setNegativeButton("确定", view1 -> {

                        })
                        .setPositiveButton("取消", view12 -> {

                        })
                        .show();
            }
        });
        mainBinding.tvSelect34.setOnClickListener(view -> {
            AddressPicker addressPicker = new AddressPicker();
            addressPicker.selectAddressDialog(MainActivity.this, mainBinding.tvSelect34);
        });
        mainBinding.tvSelect33.setOnClickListener(v -> {
            AddressDialog addressDialog = new AddressDialog(MainActivity.this);
            addressDialog.setAddressSelectResult(new AddressDialog.AddressSelectResult() {
                @Override
                public void onResult(Dialog dialog, String result) {
                    Toast.makeText(MainActivity.this, "您选择的是：" +
                            "" + result, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel(Dialog dialog) {

                }
            });
            addressDialog.selectDialog();
        });
        mainBinding.tvSelect1.setOnClickListener(v -> {
            PickerUtils.datePicker2(MainActivity.this,
                    mainBinding.layRoot, mainBinding.tvSelect1);
        });

        mainBinding.tvSelect2.setOnClickListener(v -> {
            PickerUtils.timePicker(MainActivity.this, mainBinding.tvSelect2);
        });
        mainBinding.tvDatetime.setOnClickListener(v -> {
            PickerUtils.DateTimePicker(MainActivity.this, mainBinding.tvDatetime);
        });


    }
}
