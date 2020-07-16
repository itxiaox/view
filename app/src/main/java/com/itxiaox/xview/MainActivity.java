package com.itxiaox.xview;//package com.itxiaox.xxview;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.itxiaox.address.addresspicker.AddressPicker;
import com.github.itxiaox.address.dialog.AddressDialog;
import com.itxiaox.xview.databinding.ActivityMainBinding;
import com.itxiaox.xview.dropdownlist.DropListEditText;
import com.itxiaox.xview.dropdownlist.DropListTextView;
import com.itxiaox.xview.dropdownlist.DropdownList;
import com.itxiaox.xview.picker.PickerUtils;
import com.itxiaox.xview.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    DropdownList dropdownList;
    String[] arr;
    List<String> numberList;
    DropListEditText dropList;
    ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        dropdownList = new DropdownList(MainActivity.this);
        numberList = new ArrayList<String>();
        numberList.add("菜单1");
        numberList.add("菜单2");
        numberList.add("菜单3");
        numberList.add("菜单4");
        numberList.add("菜单5");

        arr = new String[5];
        arr[0] = "建筑方";
        arr[1] = "设计方";
        arr[2] = "施工方";
        arr[3] = "咨询师";
        arr[4] = "其它";
        dropdownList.setSelectResultListener(new DropdownList.SelectResultListener() {

            @Override
            public void onResult(int position) {
                // TODO Auto-generated method stub
                mainBinding.tvSelect4.setText(arr[position] + "");
            }
        });

        numberList = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            numberList.add("188888" + i);
        }
        dropList = new DropListEditText();
        event();
    }

    private void event() {
//        AddressPicker addressPicker = new AddressPicker();
//        addressPicker.selectAddressDialog(context,textView);
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
            PickerUtils.datePicker2(MainActivity.this, mainBinding.layRoot, mainBinding.tvSelect1);
        });

        mainBinding.tvSelect2.setOnClickListener(v -> {
            PickerUtils.timePicker(MainActivity.this, mainBinding.tvSelect2);
        });

//        mainBinding.tvSelect3.setOnClickListener(v -> {
//            PickerUtils.addressPicker(MainActivity.this, mainBinding.tvSelect3);
//        });

        mainBinding.tvSelect4.setOnClickListener(v -> {
            ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this,
                    R.layout.xx_list_dropdown, arr);
            dropdownList.selectDialog(MainActivity.this, adapter);
        });

        mainBinding.tvSelect5.setOnClickListener(v -> {
            new DropListTextView().showpopwindow(MainActivity.this, mainBinding.tvSelect5, numberList);
        });

        mainBinding.etPhone.setOnClickListener(v -> {
            dropList.showpopwindow(MainActivity.this, mainBinding.etPhone, numberList);
        });
        mainBinding.tvDatetime.setOnClickListener(v -> {
            PickerUtils.DateTimePicker(MainActivity.this, mainBinding.tvDatetime);
        });

    }
}
