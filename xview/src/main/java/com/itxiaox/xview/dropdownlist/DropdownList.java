package com.itxiaox.xview.dropdownlist;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.itxiaox.xview.R;

public class DropdownList {

	private Context context;
	private Dialog dialog_select;
	private ListView listview;
	
	public DropdownList(Context context){
		this.context = context;
	}

	/**
	 * 下拉列表
	 * @param context
	 * @param adapter
	 */
	@SuppressWarnings("deprecation")
	public void selectDialog(Context context,final ListAdapter adapter) {

		dialog_select = new Dialog(context,R.style.dropdownlist);
		dialog_select.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_select.setCanceledOnTouchOutside(false);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.xx_dialog_dropdownlist, null);
		Window window = dialog_select.getWindow();
		WindowManager m = window.getWindowManager();
		window.setWindowAnimations(R.style.dropdownlist); // 添加动画
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		// 重新设置
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		lp.x = 0; // 新位置X坐标
		lp.y = 0; // 新位置Y坐标
		dialog_select.setContentView(view);
		dialog_select.setCancelable(true);
		dialog_select.setCanceledOnTouchOutside(true);
		lp.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
		lp.width = window.getAttributes().FILL_PARENT;
		window.setAttributes(lp);
		listview = (ListView) view.findViewById(R.id.lv_dialog_department_list);
		TextView tv_dialog_department_null = (TextView) view.findViewById(R.id.tv_dialog_department_null);
		listview.setAdapter(adapter);
		listview.setDividerHeight(0);
		if(adapter.getCount()>0){
			tv_dialog_department_null.setVisibility(View.GONE);	
		}else{
			tv_dialog_department_null.setVisibility(View.VISIBLE);	
		}
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				if (dialog_select != null) {
					dialog_select.dismiss();
				}
				
				selectResultListener.onResult(position);
//				String deptName = jsonArray.get(position).getAsJsonObject().get("deptName").getAsString();
//				department = jsonArray.get(position).getAsJsonObject().get("deptID").getAsString();
//				tv_department.setText(deptName);
				// getDataFromServer(str);

			}
		});
		if (!dialog_select.isShowing()) {
			dialog_select.show();
		}

	}
	private SelectResultListener selectResultListener;
	
	public void setSelectResultListener(SelectResultListener selectResultListener){
		this.selectResultListener = selectResultListener;
	}
	public interface SelectResultListener{
		public void  onResult(int position);
	}
}
