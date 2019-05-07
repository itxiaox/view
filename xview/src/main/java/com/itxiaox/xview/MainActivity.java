//package com.itxiaox.xxview;//package com.itxiaox.xxview;
//
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.itxiaox.xxview.dropdownlist.DropListEditText;
//import com.itxiaox.xxview.dropdownlist.DropListTextView;
//import com.itxiaox.xxview.dropdownlist.DropdownList;
//import com.itxiaox.xxview.picker.PickerUtils;
//import com.itxiaox.xxview.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//import butterknife.OnClick;
//
//public class MainActivity extends FragmentActivity implements OnClickListener{
//	@BindView(R.id.tv_select1)
//	 TextView tv_select1;
//	@BindView(R.id.tv_select2)
//	 TextView tv_select2;
//	@BindView(R.id.tv_select3)
//	 TextView tv_select3;
//	@BindView(R.id.lay_root)
//	 LinearLayout lay_root;
//	@BindView(R.id.tv_select4)
//	 TextView tv_select4;
//	@BindView(R.id.ray_droplistedit)
//	 RelativeLayout ray_droplistedit;
//	@BindView(R.id.et_phone)
//	 EditText et_phone;
//	@BindView(R.id.tv_datetime)
//	 TextView tv_datetime;
//	 DropdownList dropdownList;
//	 String[] arr;
//	 List<String> numberList;
//	 DropListEditText dropList;
//	@BindView(R.id.tv_select5)
//	 TextView tv_select5;
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.xx_activity_main);
//		ButterKnife.bind(this);
//		dropdownList = new DropdownList(MainActivity.this);
//		numberList = new ArrayList<String>();
//		numberList.add("菜单1");
//		numberList.add("菜单2");
//		numberList.add("菜单3");
//		numberList.add("菜单4");
//		numberList.add("菜单5");
//
//		 arr = new String[5];
//		 arr[0] = "建筑方";
//		 arr[1] = "设计方";
//		 arr[2] = "施工方";
//		 arr[3] = "咨询师";
//		 arr[4] = "其它";
//		dropdownList.setSelectResultListener(new DropdownList.SelectResultListener() {
//
//			@Override
//			public void onResult(int position) {
//				// TODO Auto-generated method stub
//				tv_select4.setText(arr[position]+"");
//			}
//		});
//
//		numberList = new ArrayList<String>();
//		for (int i = 0; i < 20; i++) {
//			numberList.add("188888" + i);
//		}
//		 dropList = new DropListEditText();
//	}
//	@Override
//	@OnClick({R.id.tv_select5,R.id.et_phone,R.id.tv_datetime,R.id.tv_select1,R.id.tv_select2,R.id.tv_select3,R.id.tv_select4})
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		switch(v.getId()){
//		case R.id.tv_select1:
//			PickerUtils.datePicker2(MainActivity.this, lay_root, tv_select1);
//			break;
//		case R.id.tv_select2:
//			PickerUtils.timePicker(MainActivity.this, tv_select2);
//			break;
//		case R.id.tv_select3:
//			PickerUtils.addressPicker(MainActivity.this, tv_select3);
//			break;
//		case R.id.tv_select4:
//			@SuppressWarnings({ "rawtypes", "unchecked" })
//			ArrayAdapter<String> adapter = new ArrayAdapter(MainActivity.this,
//					R.layout.xx_list_dropdown,arr);
//			dropdownList.selectDialog(MainActivity.this, adapter);
//			break;
//		case R.id.et_phone:
//			 dropList.showpopwindow(MainActivity.this, et_phone, numberList);
//			break;
//
//		case R.id.tv_datetime:
//			PickerUtils.DateTimePicker(MainActivity.this, tv_datetime);
//			break;
//		case R.id.tv_select5:
//			new DropListTextView().showpopwindow(MainActivity.this, tv_select5, numberList);
//			break;
//		}
//	}
//
//}
