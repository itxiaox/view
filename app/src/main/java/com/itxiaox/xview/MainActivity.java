package com.itxiaox.xview;
//package com.itxiaox.xxview;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.itxiaox.address.addresspicker.AddressPicker;
import com.github.itxiaox.address.dialog.AddressDialog;
import com.github.itxiaox.picker.PickerUtils;
import com.itxiaox.android.xutils.app.ContextUtils;
import com.itxiaox.xview.databinding.ActivityMainBinding;
import com.itxiaox.xview.java.DialogActivity;
import com.itxiaox.xview.java.ProgressBarActivity;
import com.itxiaox.xview.java.menu.MenuActivity;
import com.itxiaox.xview.java.view.ViewActivity;


public class MainActivity extends AppCompatActivity {


    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContextUtils.init(MainActivity.this);
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
            Intent intent = new Intent(MainActivity.this, DialogActivity.class);
            startActivity(intent);

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
