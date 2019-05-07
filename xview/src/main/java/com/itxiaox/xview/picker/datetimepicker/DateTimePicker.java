package com.itxiaox.xview.picker.datetimepicker;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.itxiaox.xview.R;


public class DateTimePicker {

	private Dialog dialog_photo;
	private View view;
	private static DateTimePicker addressPicker;
	private int currPosition = 0;
	private Context context;
	private int myear = 2015;
	private int mmonth = 01;
	private int mday = 01;
	private int mhour;
	private int mmunite;
	private int msecond;
	private String mformatDateTimeStr = "";
	private String mformatDateStr = "";
	private String mformatTimeStr = "";

	public DateTimePicker() {

	}

	public static DateTimePicker getInstance() {
		if (addressPicker == null) {
			addressPicker = new DateTimePicker();
		}
		return addressPicker;
	}

	private SelectResultListener selectResultListener;

	public interface SelectResultListener {
		public void onDateTimeResult(Dialog dialog, String formatStr, int year,
                                     int month, int day, int hour, int munite);
	}

	private SelectDateResultListener selectDateResultListener;

	public interface SelectDateResultListener {
		public void onDateResult(Dialog dialog, String formatStr, int year,
                                 int month, int day);
	}

	private SelectDateResultListener selectTimeResultListener;

	public interface SelectTimeResultListener {
		public void onTimeResult(Dialog dialog, String formatStr, int hour,
                                 int munite, int second);
	}

	/**
	 * 设置默认日期
	 * 
	 * @param date
	 */
	public void setDefaultDate(Context context, String date) {

		if (!TextUtils.isEmpty(date) && date.contains("-")) {
			try {
				String str[] = date.split("-");
				myear = Integer.parseInt(str[0]);
				mmonth = Integer.parseInt(str[1]) + 1;
				mday = Integer.parseInt(str[2]);
			} catch (Exception e) {
				Toast.makeText(context,"默认时间设置有误，例如：2015-01-03",Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 设置默认日期
	 * 
	 * @param time
	 */
	public void setDefaultTime(Context context, String time) {

		if (!TextUtils.isEmpty(time) && time.contains(":")) {
			try {
				String str[] = time.split(":");
				mhour = Integer.parseInt(str[0]);
				mmunite = Integer.parseInt(str[1]) + 1;
				msecond = Integer.parseInt(str[2]);
			} catch (Exception e) {
				Toast.makeText(context,"默认时间设置有误，例如：2015-01-03",Toast.LENGTH_SHORT).show();
			}
		}
	}

	/**
	 * 显示时间日期选择器
	 */
	public void showDateTimeDialog(final Context context, final String defaultDate,
			final SelectResultListener selectResultListener) {
		this.context = context;
		myear = 2015;
		mmonth = 0;
		mday = 1;
		mhour = 0;
		mmunite = 0;

		setDefaultDate(context, defaultDate);
		dialog_photo = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog_photo.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.xx_activity_datetimepicker, null);
		Button mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		final TextView tv_datepicker_title = (TextView) view
				.findViewById(R.id.tv_datepicker_title);
		LinearLayout llay_datetimepicker_view = (LinearLayout) view
				.findViewById(R.id.llay_datetimepicker_view);
		View wheelview = PickerView.bulder(context).setDefaultDate(defaultDate)
				.getDateAndTimePick(new PickerView.SelectResultListener() {

					@Override
					public void onDateTimeResult(String formatStr, int year,
							int month, int day, int hour, int munite) {
						// TODO Auto-generated method stub
						tv_datepicker_title.setText(formatStr + "");
						// if(selectResultListener!=null){}
						mformatDateTimeStr = formatStr;
						myear = year;
						mmonth = month;
						mday = day;
						mhour = hour;
						mmunite = munite;
					}
				});
		llay_datetimepicker_view.addView(wheelview);
		Window window = dialog_photo.getWindow();
		// 重新设置
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.ActionSheetDialogAnimation); // 添加动画
		window.setAttributes(lp);
		dialog_photo.setContentView(view);
		/*
		 * 将对话框的大小按屏幕大小的百分比设置
		 */
		WindowManager m = window.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		// p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
		p.height = lp.WRAP_CONTENT; // 高度设置为屏幕的0.6
		p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.65
		window.setAttributes(p);
		lp.x = 0; // 新位置X坐标
		lp.y = 0; // 新位置Y坐标
		dialog_photo.show();

		// 添加onclick事件
		mBtnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// showSelectedResult();
				if (TextUtils.isEmpty(mformatDateTimeStr)) {
					mformatDateTimeStr =  PickerView.bulder(context).getDefaultDate()+ " 00:00";
				}
				if (selectResultListener != null)
					selectResultListener.onDateTimeResult(dialog_photo,
							mformatDateTimeStr, myear, mmonth, mday, mhour,
							mmunite);
			}
		});
		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_photo.dismiss();
				dialog_photo = null;
			}
		});
	}

	/**
	 * 显示日期选择器
	 */
	public void showDateDialog(final Context context, final String defaultDate,
			final SelectDateResultListener selectDateResultListener) {
		this.context = context;
		myear = 2015;
		mmonth = 0;
		mday = 1;
		setDefaultDate(context, defaultDate);
		dialog_photo = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog_photo.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.xx_activity_datetimepicker, null);
		Button mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		final TextView tv_datepicker_title = (TextView) view
				.findViewById(R.id.tv_datepicker_title);
		LinearLayout llay_datetimepicker_view = (LinearLayout) view
				.findViewById(R.id.llay_datetimepicker_view);
		View wheelview = PickerView.bulder(context).setDefaultDate(defaultDate)
				.getDatePick(new PickerView.SelectDateResultListener() {
					@Override
					public void onDateResult(String formatStr, int year,
							int month, int day) {
						mformatDateStr = formatStr;
						myear = year;
						mmonth = month;
						mday = day;
						tv_datepicker_title.setText(mformatDateStr);
					}
				});
		llay_datetimepicker_view.addView(wheelview);
		Window window = dialog_photo.getWindow();
		// 重新设置
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.ActionSheetDialogAnimation); // 添加动画
		window.setAttributes(lp);
		dialog_photo.setContentView(view);
		/*
		 * 将对话框的大小按屏幕大小的百分比设置
		 */
		WindowManager m = window.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		// p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
		p.height = lp.WRAP_CONTENT; // 高度设置为屏幕的0.6
		p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.65
		window.setAttributes(p);
		lp.x = 0; // 新位置X坐标
		lp.y = 0; // 新位置Y坐标
		dialog_photo.show();

		// 添加onclick事件
		mBtnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// showSelectedResult();
				if (TextUtils.isEmpty(mformatDateStr)) {
					mformatDateStr  =  PickerView.bulder(context).getDefaultDate();
				}
				if (selectDateResultListener != null)
					selectDateResultListener.onDateResult(dialog_photo,
							mformatDateStr, myear, mmonth, mday);
			}
		});
		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_photo.dismiss();
				dialog_photo = null;
			}
		});
	}

	/**
	 * 显示时间选择器
	 */
	public void showTimeDialog(Context context, final String defaultTime,
			final SelectTimeResultListener selectTimeResultListener) {
		this.context = context;
		mhour = 12;
		mmunite = 0;
		msecond = 0;
		setDefaultTime(context, defaultTime);
		dialog_photo = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog_photo.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.xx_activity_datetimepicker, null);
		Button mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		final TextView tv_datepicker_title = (TextView) view
				.findViewById(R.id.tv_datepicker_title);
		LinearLayout llay_datetimepicker_view = (LinearLayout) view
				.findViewById(R.id.llay_datetimepicker_view);
		View wheelview = PickerView.bulder(context).setDefaultTime("13:00:00")
				.getTimePick(new PickerView.SelectTimeResultListener() {

					@Override
					public void onTimeResult(String formatStr, int hour,
							int munite, int second) {
						mformatTimeStr = formatStr;
						mhour = hour;
						mmunite = munite;
						msecond = second;
						tv_datepicker_title.setText(mformatTimeStr);
					}
				});
		llay_datetimepicker_view.addView(wheelview);
		Window window = dialog_photo.getWindow();
		// 重新设置
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.ActionSheetDialogAnimation); // 添加动画
		window.setAttributes(lp);
		dialog_photo.setContentView(view);
		/*
		 * 将对话框的大小按屏幕大小的百分比设置
		 */
		WindowManager m = window.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		// p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
		p.height = lp.WRAP_CONTENT; // 高度设置为屏幕的0.6
		p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.65
		window.setAttributes(p);
		lp.x = 0; // 新位置X坐标
		lp.y = 0; // 新位置Y坐标
		dialog_photo.show();

		// 添加onclick事件
		mBtnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// showSelectedResult();
				if (TextUtils.isEmpty(mformatTimeStr)) {
					mformatTimeStr = defaultTime;
				}
				if (selectTimeResultListener != null)
					selectTimeResultListener.onTimeResult(dialog_photo,
							mformatTimeStr, mhour, mmunite, msecond);
			}
		});
		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog_photo.dismiss();
				dialog_photo = null;
			}
		});
	}

}
