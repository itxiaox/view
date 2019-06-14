package com.itxiaox.xview.picker;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.itxiaox.xview.picker.addresspicker.AddressPicker;
import com.itxiaox.xview.picker.datepicker.DatePicker;
import com.itxiaox.xview.picker.datepicker.DatePicker2;
import com.itxiaox.xview.picker.datepicker.TimePicker;
import com.itxiaox.xview.picker.slidedatetimepicker.SlideDateTimeListener;
import com.itxiaox.xview.picker.slidedatetimepicker.SlideDateTimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class PickerUtils extends FragmentActivity{

	
	private static DatePicker datePicker;
	private static TimePicker timPicker;
	private static DatePicker2 datePicker2;

	/**
	* @author 肖骁 
	* @Title: datePicker 
	* @Description: 日期选择框
	* @param @param context
	* @param @param rootview
	* @param @param tv_consum_createtime    
	* @return void    返回类型 
	* @throws
	 */
	public static void datePicker(Activity context,View rootview ,View tv_consum_createtime) {
		datePicker = new DatePicker(context, tv_consum_createtime,"1965-1-1");
		datePicker.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
	}
	public static void datePicker2(Activity context,View rootview ,View tv_consum_createtime) {
		 datePicker2 = new DatePicker2(context);
		 datePicker2.selectDateDialog((TextView)tv_consum_createtime,"1965-1-1");
	}
	
	public static void timePicker(Context context,TextView textview){
		 timPicker = new TimePicker(context);
		 timPicker.selectTimeDialog(textview);
	}
	
	public static void addressPicker(Context context,TextView textView){
		AddressPicker addressPicker = new AddressPicker();
		addressPicker.selectAddressDialog(context,textView);
	}
	/**
	 * 时间日期选择器，注意使用此方法时候，调用此方法的Activity必须继承FragmentActivity
	 * @param context
	 * @param textView
	 */
	public static void DateTimePicker(final Context context,final TextView textView){
	    new SlideDateTimePicker.Builder(((FragmentActivity) context).getSupportFragmentManager())
      .setListener(new SlideDateTimeListener() {
		
    	  @Override
          public void onDateTimeSet(Date date)
          {
//              Toast.makeText(context,
//            		  date.toString(), Toast.LENGTH_SHORT).show();
    		  textView.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date));
          }

          // Optional cancel listener
          @Override
          public void onDateTimeCancel()
          {
              Toast.makeText(context,
                      "Canceled", Toast.LENGTH_SHORT).show();
          }
	})
      .setInitialDate(new Date())
      //.setMinDate(minDate)
      //.setMaxDate(maxDate)
      .setIs24HourTime(true)
      .setTheme(SlideDateTimePicker.HOLO_DARK)
      //.setIndicatorColor(Color.parseColor("#990000"))
      .build()
      .show();
	}
	
}
