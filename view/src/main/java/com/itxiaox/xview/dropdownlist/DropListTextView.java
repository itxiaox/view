package com.itxiaox.xview.dropdownlist;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.itxiaox.xview.R;

import java.util.List;
/**
 * 
 */
public class DropListTextView {

	
	private PopupWindow popwindow;
	private ListView lv_list=null;
	private List<String> numberList;

	public void showpopwindow(Context context,final TextView textView,final List<String> numberList){
		lv_list = new ListView(context);
//		lv_list.setDivider(null);
		lv_list.setDividerHeight(1);
		lv_list.setSelector(android.R.drawable.list_selector_background);
		lv_list.setBackgroundResource(R.drawable.listview_background);
		this.numberList = numberList;
//		for (int i = 0; i < 20; i++) {
//			numberList.add("188888" + i);
//		}
		LayoutParams param = lv_list.getLayoutParams();
		// 绑定数据
		lv_list.setAdapter(new MyNumberAdapter(context,numberList));
		popwindow = new PopupWindow(lv_list, textView.getWidth() - 4, param.WRAP_CONTENT);
		
		// 设置点击外部可以被关闭.
		popwindow.setOutsideTouchable(true);
		popwindow.setBackgroundDrawable(new BitmapDrawable());
		
		// 获取焦点
		popwindow.setFocusable(true);
		// 显示在输入框的左下角位置
		popwindow.showAsDropDown(textView, 2, -4);
		
		
		lv_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String number = numberList.get(position);
				textView.setText(number);
				popwindow.dismiss();
			}
		});
	}
	class MyNumberAdapter extends BaseAdapter {
		private Context context;
		private List<String> numberList;
		public MyNumberAdapter(Context context,List<String> numberList){
			this.context = context;
			this.numberList = numberList;
		}
		@Override
		public int getCount() {
			return numberList.size();
		}
		
		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {
			NumberViewHolder mHolder = null;
			if(convertView == null) {
				convertView = View.inflate(context, R.layout.xx_droplistedittext_number_item, null);
				mHolder = new NumberViewHolder();
				mHolder.tvNumber = (TextView) convertView.findViewById(R.id.tv_number_item);
				convertView.setTag(mHolder);
			} else {
				// 取出holder类
				mHolder = (NumberViewHolder) convertView.getTag();
			}
			// 给mHolder类中的对象赋值.
			mHolder.tvNumber.setText(numberList.get(position));
//			mHolder.ibDelete.setOnClickListener(new OnClickListener() {
//
//				@Override
//				public void onClick(View v) {
//					numberList.remove(position);
//
//					// 刷新界面
//					notifyDataSetChanged();
//				}
//			});
			return convertView;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}
	}
	
	class NumberViewHolder {
		public TextView tvNumber;
		public ImageButton ibDelete;
	}
}
