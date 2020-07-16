package com.github.itxiaox.picker.datepicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.github.itxiaox.picker.R;

import java.util.Calendar;

public class DatePicker2 {

	private Dialog dialog_time;
	private Context context;
	private boolean timeChanged = false;
	private boolean timeScrolled = false;
	StringBuffer buffer;
	private TextView view;
	private String age;
	private int mCurYear;
	private int mCurMonth;
	private String[] dateType;
	private View btn_submit;
	private TextView submit;
	private TextView cancel;
	private DateNumericAdapter monthAdapter, dayAdapter, yearAdapter;
	private int mCurDay;
	private int textSize = 20;
	public DatePicker2(Context context){
		this.context = context;
	} 

	/**
	 * 选择时间对话框
	 * @param tv_time
	 */
	@SuppressWarnings("deprecation")
	public void selectDateDialog(final TextView tv_time,String defaultTime) {
		buffer = new StringBuffer();
		dialog_time = new Dialog(context, R.style.Dialog_Fullscreen);
		dialog_time.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.xx_datepiker, null);
		int screenWidth =((Activity)context).getWindowManager().getDefaultDisplay().getWidth();

		Window window = dialog_time.getWindow();
		// 重新设置
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		// window.setWindowAnimations(R.style.mystyle); // 添加动画
		lp.x = 0; // 新位置X坐标
		lp.y = 0; // 新位置Y坐标
		// lp.width = screenWidth;
		// window.setAttributes(lp);
		view.setMinimumWidth(screenWidth - 0);
		dialog_time.setContentView(view, lp);

		 submit = (TextView) view.findViewById(R.id.submit);
		 cancel = (TextView) view.findViewById(R.id.cancel);
//		final WheelView year = (WheelView) view.findViewById(R.id.year);
		final WheelView year = (WheelView) view.findViewById(R.id.year);
		final WheelView month = (WheelView) view.findViewById(R.id.month);
		final WheelView day = (WheelView) view.findViewById(R.id.day);
		// TextView tv_title = (TextView)
		// view.findViewById(R.id.dialog_time_title);

		
		try {
		   this.view = (TextView)view;
			String time = ((TextView)view).getText().toString().trim();
			if(TextUtils.isEmpty(time)){
				if(TextUtils.isEmpty(defaultTime)){
				 this.age = "1980-1-1";
				}else{
					this.age = defaultTime;
				}
			}else{
				this.age = time;
			}
		} catch (Exception e) {
			// TODO: handle exception
			this.age = "1980-1-1";
		}
		
		
		submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				((TextView) tv_time).setText("" + age);
				dialog_time.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_time.dismiss();
			}
		});
		Calendar calendar = Calendar.getInstance();
		OnWheelChangedListener listener = new OnWheelChangedListener() {
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				updateDays(year, month, day);

			}

			@Override
			public void onChanged(WheelViewTime wheelViewTime, int oldValue, int newValue) {
				
				
			}
		};
		int curYear = calendar.get(Calendar.YEAR);
		if (age != null && age.contains("-")) {
			String str[] = age.split("-");
			mCurYear = 100 - (curYear - Integer.parseInt(str[0]));
			mCurMonth = Integer.parseInt(str[1]) - 1;
			mCurDay = Integer.parseInt(str[2]) - 1;
			;
		}
		dateType = context.getResources().getStringArray(R.array.date);
		monthAdapter = new DateNumericAdapter(context, 1, 12, 5);
		monthAdapter.setTextType(dateType[1]);
		month.setViewAdapter(monthAdapter);
		month.setCurrentItem(mCurMonth);
		month.addChangingListener(listener);
		// year

		yearAdapter = new DateNumericAdapter(context, curYear - 100, curYear + 100, 100 - 20);
		yearAdapter.setTextType(dateType[0]);
		year.setViewAdapter(yearAdapter);
		year.setCurrentItem(mCurYear);
		year.addChangingListener(listener);
		// day

		updateDays(year, month, day);
		day.setCurrentItem(mCurDay);
		updateDays(year, month, day);
		day.addChangingListener(listener);

		dialog_time.show();
	}
	
	public void updateDays(WheelView year, WheelView month, WheelView day) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + year.getCurrentItem());
		calendar.set(Calendar.MONTH, month.getCurrentItem());

		int maxDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		dayAdapter = new DateNumericAdapter(context, 1, maxDays, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		dayAdapter.setTextType(dateType[2]);
		day.setViewAdapter(dayAdapter);
		int curDay = Math.min(maxDays, day.getCurrentItem() + 1);
		day.setCurrentItem(curDay - 1, true);
		int years = calendar.get(Calendar.YEAR) - 100;
		age = years + "-" + (month.getCurrentItem() + 1) + "-" + (day.getCurrentItem() + 1);
	}

	/**
	 * Adapter for numeric wheels. Highlights the current value.
	 */
	private class DateNumericAdapter extends NumericWheelAdapter {
		// Index of current item
		int currentItem;
		// Index of item to be highlighted
		int currentValue;

		/**
		 * Constructor
		 */
		public DateNumericAdapter(Context context, int minValue, int maxValue, int current) {
			super(context, minValue, maxValue);
			this.currentValue = current;
			setTextSize(textSize);
		}

		protected void configureTextView(TextView view) {
			super.configureTextView(view);
			view.setTypeface(Typeface.SANS_SERIF);
		}

		public CharSequence getItemText(int index) {
			currentItem = index;
			return super.getItemText(index);
		}

	}
	
}
