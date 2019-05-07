package com.itxiaox.xview.picker.datetimepicker;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.itxiaox.xview.R;

import java.util.Calendar;

public class PickerView {
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView time;
	private WheelView hour;
	private WheelView min;
	private WheelView sec;

	private int minYear;//最小年份
	private int maxYear;//最大年份
	private int mYear = 2015;
	private int mMonth = 0;
	private int mDay = 1;
	private int mHour = 0;
	private int mMunite = 0;
	private int mSecond = 0;
	private static Context mContext;
	View view = null;

	boolean isMonthSetted = false, isDaySetted = false;
	private LayoutInflater inflater;
	static PickerView pickerView;
	private String defalutTime;
	public void dateAndTimePicker(String defalutTime) {
		this.defalutTime = defalutTime;
	}

	public PickerView(Context context) {
		mContext = context;
		inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public static PickerView bulder(Context context) {
		if (pickerView == null) {
			pickerView = new PickerView(context);
		}
		return pickerView;
	}
	/**
	 * 设置默认日期
	 * @param date
	 */
	public PickerView setDefaultDate(String date){
		
		if (!TextUtils.isEmpty(date)&& date.contains("-")) {
			try {
				String str[] = date.split("-");
				mYear = Integer.parseInt(str[0]);
				mMonth = Integer.parseInt(str[1])+1;
				mDay = Integer.parseInt(str[2]);
			} catch (Exception e) {
				Toast.makeText(mContext,"默认时间设置有误，例如：2015-01-03",Toast.LENGTH_SHORT).show();
			}
//			mCurYear = 100 - (curYear - Integer.parseInt(str[0]));
//			mCurMonth = Integer.parseInt(str[1]) - 1;
//			mCurDay = Integer.parseInt(str[2]) - 1;
//			;
		}
		return pickerView;
	}
	
	public String getDefaultDate(){
		String birthday = new StringBuilder()
		.append(mYear)
		.append("-")
		.append((mMonth+1) < 10 ? "0"
				+ (mMonth+1) : (mMonth+1))
		.append("-")
		.append((mDay < 10) ? "0"
				+ mDay : mDay)
		.toString();
		return birthday;
	}
	/**
	 * 设置默认时间
	 * @param time
	 */
	public PickerView setDefaultTime(String time){
		
		if (!TextUtils.isEmpty(time)&& time.contains(":")) {
			try {
				String str[] = time.split(":");
				mHour = Integer.parseInt(str[0]);
				mMunite = Integer.parseInt(str[1]);
				mSecond = Integer.parseInt(str[2]);
			} catch (Exception e) {
				Toast.makeText(mContext,"默认时间设置有误，例如：12:00:00",Toast.LENGTH_SHORT).show();
			}
		}
		return pickerView;
	}
	
	private SelectResultListener selectResultListener;

	public interface SelectResultListener {
		public void onDateTimeResult(String formatStr, int year, int month,
                                     int day, int hour, int munite);
	}

	public interface SelectDateResultListener {
		public void onDateResult(String formatStr, int year, int month, int day);
	}

	public interface SelectTimeResultListener {
		public void onTimeResult(String formatStr, int hour, int munite,
                                 int second);
	}

	public View getDateAndTimePick(SelectResultListener selectResultListener) {

		Calendar c = Calendar.getInstance();
		maxYear = c.get(Calendar.YEAR)+50;//最大时间
		minYear = c.get(Calendar.YEAR)-50;
		// int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
		// int curDate = c.get(Calendar.DATE);

		int curYear = mYear;
		int curMonth = mMonth + 1;
		int curDate = mDay;
		
		
		DateTimeScrollListener dateTimeScrollListener = new DateTimeScrollListener(
				selectResultListener);
		view = inflater.inflate(R.layout.wheel_datetime_picker, null);

		year = (WheelView) view.findViewById(R.id.year);
		NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(
				mContext, minYear, maxYear);
		numericWheelAdapter1.setLabel("年");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);// 是否可循环滑动
		year.addScrollingListener(dateTimeScrollListener);

		month = (WheelView) view.findViewById(R.id.month);
		NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(
				mContext, 1, 12, "%02d");
		numericWheelAdapter2.setLabel("月");
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(true);
		month.addScrollingListener(dateTimeScrollListener);

		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear, curMonth);
		day.setCyclic(true);
		day.addScrollingListener(dateTimeScrollListener);
		// time= (WheelView) view.findViewById(R.id.time);
		// String[] times = {"上午","下午"} ;
		// ArrayWheelAdapter<String> arrayWheelAdapter=new
		// ArrayWheelAdapter<String>(MainActivity.this,times );
		// time.setViewAdapter(arrayWheelAdapter);
		// time.setCyclic(false);
		// time.addScrollingListener(scrollListener);

		hour = (WheelView) view.findViewById(R.id.hour);
		NumericWheelAdapter numericWheelAdapter3 = new NumericWheelAdapter(
				mContext, 1, 23, "%02d");
		numericWheelAdapter3.setLabel("时");
		hour.setViewAdapter(numericWheelAdapter3);
		hour.setCyclic(true);
		hour.addScrollingListener(dateTimeScrollListener);

		min = (WheelView) view.findViewById(R.id.min);
		NumericWheelAdapter numericWheelAdapter4 = new NumericWheelAdapter(
				mContext, 0, 59, "%02d");
		numericWheelAdapter4.setLabel("分");
		min.setViewAdapter(numericWheelAdapter4);
		min.setCyclic(true);
		min.addScrollingListener(dateTimeScrollListener);

		year.setVisibleItems(7);// 设置显示行数
		month.setVisibleItems(7);
		day.setVisibleItems(7);
		// time.setVisibleItems(7);
		hour.setVisibleItems(7);
		min.setVisibleItems(7);

		year.setCurrentItem(curYear - minYear);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);

		return view;
	}

	public View getDatePick(SelectDateResultListener selectDateResultListener) {
		Calendar c = Calendar.getInstance();
		maxYear = c.get(Calendar.YEAR)+50;//最大时间
		minYear = c.get(Calendar.YEAR)-50;
		int curYear = mYear;
		int curMonth = mMonth + 1;
		int curDate = mDay;
		DateScrollListener dateScrollListener = new DateScrollListener(
				selectDateResultListener);
		view = inflater.inflate(R.layout.wheel_date_picker, null);
		year = (WheelView) view.findViewById(R.id.year);
		NumericWheelAdapter numericWheelAdapter1 = new NumericWheelAdapter(
				mContext, minYear, maxYear);
		numericWheelAdapter1.setLabel("年");
		year.setViewAdapter(numericWheelAdapter1);
		year.setCyclic(true);// 是否可循环滑动
		year.addScrollingListener(dateScrollListener);
		month = (WheelView) view.findViewById(R.id.month);
		NumericWheelAdapter numericWheelAdapter2 = new NumericWheelAdapter(
				mContext, 1, 12, "%02d");
		numericWheelAdapter2.setLabel("月");
		month.setViewAdapter(numericWheelAdapter2);
		month.setCyclic(true);
		month.addScrollingListener(dateScrollListener);
		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear, curMonth);
		day.setCyclic(true);
		day.addScrollingListener(dateScrollListener);
		
		year.setVisibleItems(7);// 设置显示行数
		month.setVisibleItems(7);
		day.setVisibleItems(7);
		year.setCurrentItem(curYear - minYear);
		month.setCurrentItem(mMonth-2);
		day.setCurrentItem(curDate - 1);

		return view;
	}

	public View getTimePick(SelectTimeResultListener selectTimeResultListener) {

		view = inflater.inflate(R.layout.wheel_time_picker, null);

		TimeScrollListener timeScrollListener = new TimeScrollListener(
				selectTimeResultListener);
		
		// time= (WheelView) view.findViewById(R.id.time);
		// String[] times = {"上午","下午"} ;
		// ArrayWheelAdapter<String> arrayWheelAdapter=new
		// ArrayWheelAdapter<String>(MainActivity.this,times );
		// time.setViewAdapter(arrayWheelAdapter);
		// time.setCyclic(false);
		// time.addScrollingListener(scrollListener);
		
		hour = (WheelView) view.findViewById(R.id.hour);
		NumericWheelAdapter numericWheelAdapter3 = new NumericWheelAdapter(
				mContext, 1, 23, "%02d");
		numericWheelAdapter3.setLabel("时");
		hour.setViewAdapter(numericWheelAdapter3);
		hour.setCyclic(true);
		hour.addScrollingListener(timeScrollListener);

		min = (WheelView) view.findViewById(R.id.min);
		NumericWheelAdapter numericWheelAdapter4 = new NumericWheelAdapter(
				mContext, 0, 59, "%02d");
		numericWheelAdapter4.setLabel("分");
		min.setViewAdapter(numericWheelAdapter4);
		min.setCyclic(true);
		min.addScrollingListener(timeScrollListener);
		sec = (WheelView) view.findViewById(R.id.sec);
		NumericWheelAdapter numericWheelAdapter5 = new NumericWheelAdapter(
				mContext, 0, 59, "%02d");
		numericWheelAdapter5.setLabel("秒");
		sec.setViewAdapter(numericWheelAdapter5);
		sec.setCyclic(true);
		sec.addScrollingListener(timeScrollListener);
		hour.setVisibleItems(7);
		min.setVisibleItems(7);
		sec.setVisibleItems(7);
		
		hour.setCurrentItem(mHour-2);
		min.setCurrentItem(mMunite);
		sec.setCurrentItem(mSecond);
		return view;
	}

	/**
	 * 时间日期回调
	 * 
	 * @ClassName: DateTimeScrollListener
	 * @Description: TODO
	 * @author xiaoxiao
	 * @date modify by 2015-12-16 上午11:28:19
	 * 
	 */
	public class DateTimeScrollListener implements OnWheelScrollListener {

		private SelectResultListener selectResultListener;

		public DateTimeScrollListener(SelectResultListener selectResultListener) {
			this.selectResultListener = selectResultListener;
		}

		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			int n_year = year.getCurrentItem() + minYear;// 年
			int n_month = month.getCurrentItem() + 1;// 月
			int n_hour = hour.getCurrentItem() + 1;// 时
			int n_min = min.getCurrentItem() + 1;// 分
			initDay(n_year, n_month);

			String birthday = new StringBuilder()
					.append((year.getCurrentItem() + minYear))
					.append("-")
					.append((month.getCurrentItem() + 1) < 10 ? "0"
							+ (month.getCurrentItem() + 1) : (month
							.getCurrentItem() + 1))
					.append("-")
					.append(((day.getCurrentItem() + 1) < 10) ? "0"
							+ (day.getCurrentItem() + 1) : (day
							.getCurrentItem() + 1))
					.append(" ")
					.append(((hour.getCurrentItem() + 1) < 10) ? "0"
							+ (hour.getCurrentItem() + 1) : (hour
							.getCurrentItem() + 1))
					.append(":")
					.append(((min.getCurrentItem() + 1) < 10) ? "0"
							+ (min.getCurrentItem() + 1) : (min
							.getCurrentItem() + 1)).toString();
			if (selectResultListener != null) {
				selectResultListener.onDateTimeResult(birthday, n_year,
						n_month, day.getCurrentItem(), n_hour, n_min);
			}
		}
	};

	/**
	 * 日期回调
	 * 
	 * @ClassName: DateScrollListener
	 * @Description: TODO
	 * @author xiaoxiao
	 * @date modify by 2015-12-16 上午11:30:55
	 * 
	 */
	public class DateScrollListener implements OnWheelScrollListener {
		private SelectDateResultListener selectDateResultListener;

		public DateScrollListener(
				SelectDateResultListener selectDateResultListener) {
			this.selectDateResultListener = selectDateResultListener;
		}

		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			int n_year = year.getCurrentItem() + minYear;// 年
			int n_month = month.getCurrentItem() + 1;// 月

			initDay(n_year, n_month);

			String birthday = new StringBuilder()
					.append((year.getCurrentItem() + minYear))
					.append("-")
					.append((month.getCurrentItem() + 1) < 10 ? "0"
							+ (month.getCurrentItem() + 1) : (month
							.getCurrentItem() + 1))
					.append("-")
					.append(((day.getCurrentItem() + 1) < 10) ? "0"
							+ (day.getCurrentItem() + 1) : (day
							.getCurrentItem() + 1)).toString();

			if (selectDateResultListener != null) {
				selectDateResultListener.onDateResult(birthday, n_year,
						n_month, day.getCurrentItem());
			}
			// tv1.setText("日期           "+birthday);
			// Toast.makeText(mContext, "您选择的日期：" + birthday, 0).show();
		}
	};

	/**
	 * 时间选择回调
	 * 
	 * @ClassName: TimeScrollListener
	 * @Description: TODO
	 * @author xiaoxiao
	 * @date modify by 2015-12-16 下午12:08:16
	 * 
	 */
	public class TimeScrollListener implements OnWheelScrollListener {
		private SelectTimeResultListener selectTimeResultListener;

		public TimeScrollListener(
				SelectTimeResultListener selectTimeResultListener) {
			this.selectTimeResultListener = selectTimeResultListener;
		}

		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			int n_hour = hour.getCurrentItem() + 1;// 时
			int n_munite = min.getCurrentItem() + 1;// 时
			int n_sec = sec.getCurrentItem() + 1;// 分

			String birthday = new StringBuilder()
					.append(((hour.getCurrentItem() + 1) < 10) ? "0"
							+ (hour.getCurrentItem() + 1) : (hour
							.getCurrentItem() + 1))
					.append(":")
					.append(((min.getCurrentItem() + 1) < 10) ? "0"
							+ (min.getCurrentItem() + 1) : (min
							.getCurrentItem() + 1))
					.append(":")
					.append(((sec.getCurrentItem() + 1) < 10) ? "0"
							+ (sec.getCurrentItem() + 1) : (sec
							.getCurrentItem() + 1)).toString();
			// Toast.makeText(mContext, "您选择的时间：" + birthday, 0).show();
			// tv1.setText("日期           "+birthday);
			if (selectTimeResultListener != null) {
				selectTimeResultListener.onTimeResult(birthday, n_hour,
						n_munite, n_sec);
			}
		}
	};

	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 */
	private void initDay(int arg1, int arg2) {
		NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(
				mContext, 1, getDay(arg1, arg2), "%02d");
		numericWheelAdapter.setLabel("日");
		day.setViewAdapter(numericWheelAdapter);
	}
}
