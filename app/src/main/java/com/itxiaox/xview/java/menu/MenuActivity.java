package com.itxiaox.xview.java.menu;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.itxiaox.menu.dropdownlist.DropListEditText;
import com.github.itxiaox.menu.dropdownlist.DropListTextView;
import com.github.itxiaox.menu.dropdownlist.DropdownList;
import com.github.itxiaox.menu.popupwindow.PopupWindowManger;
import com.itxiaox.android.xutils.ui.DensityUtils;
import com.itxiaox.xview.R;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {
    String[] arr;
    List<String> numberList;
    DropListEditText dropList;
    DropdownList dropdownList;
    com.itxiaox.xview.databinding.ActivityMenuBinding menuBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuBinding = com.itxiaox.xview.databinding.ActivityMenuBinding.inflate(getLayoutInflater());
        setContentView(menuBinding.getRoot());
        dropdownList();
        menu();
    }

    private void menu(){
        menuBinding.tvDropdownlist1.setOnClickListener(v -> {
            ArrayAdapter<String> adapter = new ArrayAdapter(MenuActivity.this,
                    R.layout.xx_list_dropdown, arr);
            dropdownList.selectDialog(MenuActivity.this, adapter);
        });

        menuBinding.tvDropdownlist2.setOnClickListener(v -> {
            new DropListTextView().showpopwindow(
                    MenuActivity.this,menuBinding.tvDropdownlist1, numberList);
        });

//        menuBinding.etPhone.setOnClickListener(v -> {
//            dropList.showpopwindow(MainActivity.this, mainBinding.etPhone, numberList);
//        });
        DensityUtils.init(this);
        menuBinding.tvPopupwindow.setOnClickListener(view -> {
          PopupWindow popupWindow =   PopupWindowManger.showPopupWindow(MenuActivity.this, DensityUtils.dip2px(200)
                  ,numberList, new PopupWindowManger.PopupWindowListener() {
                @Override
                public void onDismiss(PopupWindow popupWindow) {

                }

                @Override
                public void onItemClick(PopupWindow popupWindow, AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(MenuActivity.this, ""+numberList.get(position),
                            Toast.LENGTH_SHORT).show();
                }
            });
            popupWindow.showAsDropDown(view);
        });
    }

    private void dropdownList() {
        dropdownList = new DropdownList(MenuActivity.this);
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
                menuBinding.tvDropdownlist1.setText(arr[position] + "");
            }
        });

        numberList = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            numberList.add("188888" + i);
        }
        dropList = new DropListEditText();
    }

}