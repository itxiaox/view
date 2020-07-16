package com.github.itxiaox.picker.datepicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.github.itxiaox.picker.R;

import java.util.Calendar;

public class TimePicker {

	private Dialog dialog_time;
	private Context context;
	private boolean timeChanged = false;
	private boolean timeScrolled = false;
	StringBuffer buffer;
	public TimePicker(Context context){
		this.context = context;
	} 

	/**
	 * 选择时间对话框
	 * @param tv_time
	 */
	@SuppressWarnings("deprecation")
	public void selectTimeDialog(final TextView tv_time) {
		buffer = new StringBuffer();
		dialog_time = new Dialog(context, R.style.Dialog_Fullscreen);
		dialog_time.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.xx_timepicker, null);
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

		TextView tv_sure = (TextView) view.findViewById(R.id.submit);
		TextView tv_cancle = (TextView) view.findViewById(R.id.cancel);
		final WheelViewTime wv_hour = (WheelViewTime) view.findViewById(R.id.dialog_time_hour);
		final WheelViewTime wv_minute = (WheelViewTime) view.findViewById(R.id.dialog_time_minute);
		// TextView tv_title = (TextView)
		// view.findViewById(R.id.dialog_time_title);
		wv_hour.setAdapter(new NumericWheelAdapter(context, 0, 23));
		wv_hour.setLabel("时");
		wv_hour.setCyclic(true);

		wv_minute.setAdapter(new NumericWheelAdapter(context, 0, 60));
		wv_minute.setLabel("分");
		wv_minute.setCyclic(true);
		Calendar c = Calendar.getInstance();
		int currentHour = c.get(Calendar.HOUR_OF_DAY);
		int currentMinute = c.get(Calendar.MINUTE);
		wv_hour.setCurrentItem(currentHour);
		wv_minute.setCurrentItem(currentMinute);

		/*
		 * addChangingListener(wv_hour, "H"); addChangingListener(wv_minute, "M");
		 */

		OnWheelChangedListener wheelChangedListener = new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelViewTime wheel, int oldValue, int newValue) {
				if (!timeScrolled) {// 滚轮滚动了
					timeChanged = true;// 时间数据改变了
					// picker设置为当前滚动的数据
					// picker.setCurrentHour(wv_hour.getCurrentItem());
					// picker.setCurrentMinute(wv_minute.getCurrentItem());
					// 将时间改变标志置为false
					timeChanged = false;
				}

			}

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {

			}
		};

		// 为小时和分钟添加滚轮变化监听器
		wv_hour.addChangingListener(wheelChangedListener);
		wv_minute.addChangingListener(wheelChangedListener);

		OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelViewTime wheel) {
				timeScrolled = true;
			}

			@Override
			public void onScrollingFinished(WheelViewTime wheel) {
				timeScrolled = false;
				timeChanged = true;
				// picker.setCurrentHour(wv_hour.getCurrentItem());
				// picker.setCurrentMinute(wv_minute.getCurrentItem());
				timeChanged = false;
			}

			@Override
			public void onScrollingStarted(WheelView wheel) {

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {

			}
		};

		// 为小时和分钟添加滚轮滚动监听器
		wv_hour.addScrollingListener(scrollListener);
		wv_minute.addScrollingListener(scrollListener);
		
		tv_sure.setOnClickListener(new OnClickListener() {

		

			@Override
			public void onClick(View v) {
				String hour = wv_hour.getCurrentItem()+"";
				String minute = wv_minute.getCurrentItem()+"";
				if(hour.length()==1){
					hour = "0"+hour;
				}
				if(minute.length()==1){
					minute = "0"+minute;
				}
				String time = hour + ":" + minute;
				
				tv_time.setText(time);
				buffer.append(time + ",");
				dialog_time.dismiss();
			}
		});

		tv_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog_time.dismiss();
			}
		});
		dialog_time.show();
	}
}
