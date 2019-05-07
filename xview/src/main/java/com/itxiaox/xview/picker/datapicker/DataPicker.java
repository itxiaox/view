package com.itxiaox.xview.picker.datapicker;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.itxiaox.xview.R;

import java.util.List;

public class DataPicker {

	private Dialog dialog_photo;
	private View view;
	private static DataPicker addressPicker;
	private int currPosition = 0;
	private Context context;

	public DataPicker() {

	}

	public static DataPicker getInstance() {
		if (addressPicker == null) {
			addressPicker = new DataPicker();
		}
		return addressPicker;
	}

	private SelectResultListener selectResultListener;

	public interface SelectResultListener {
		public void onResult(Dialog dialog, int position);
	}

	/**
	 * 显示数据选择器
	 */
	public void selectDataDialog(Context context, final List<String> list,
			final SelectResultListener selectResultListener) {
		this.context = context;

		dialog_photo = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog_photo.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.xx_activity_datapicker, null);

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
		WheelView id_data = (WheelView) view.findViewById(R.id.id_data);
		final TextView tv_datapicker_title = (TextView) view
				.findViewById(R.id.tv_datapicker_title);
		Button mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		// 添加change事件
		id_data.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				// if (wheel == mViewProvince) {
				// updateCities();
				// } else if (wheel == mViewCity) {
				// updateAreas();
				// } else if (wheel == mViewDistrict) {
				// mCurrentDistrictName =
				// mDistrictDatasMap.get(mCurrentCityName)[newValue];
				// mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
				// }
				String result = list.get(newValue);
				tv_datapicker_title.setText(result);
				currPosition = newValue;
			}
		});
		id_data.addClickingListener(new OnWheelClickedListener() {

			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {
				// TODO Auto-generated method stub
				String result = list.get(itemIndex);
				tv_datapicker_title.setText(result);
				currPosition = itemIndex;
				if (selectResultListener != null)
					selectResultListener.onResult(dialog_photo, currPosition);
			}
		});
		// 添加onclick事件
		mBtnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// showSelectedResult();
				if (selectResultListener != null)
					selectResultListener.onResult(dialog_photo, currPosition);
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
		id_data.setViewAdapter(new ArrayWheelAdapter<String>(context, list));
		// 设置可见条目数量
		id_data.setVisibleItems(7);
		dialog_photo.show();
	}

	private int setDefalutValue(String defaultString, List<String> list) {
		int defaultPositon = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals(defaultString)) {
				defaultPositon = i;
				break;
			}
		}
		return defaultPositon;
	}

	/**
	 * 显示数据选择器
	 */
	public void selectDataDialog(Context context, String defaultString,
			final List<String> list,
			final SelectResultListener selectResultListener) {
		this.context = context;
		int currPostion = setDefalutValue(defaultString, list);
		dialog_photo = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog_photo.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.xx_activity_datapicker, null);

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
		final WheelView id_data = (WheelView) view.findViewById(R.id.id_data);
		final TextView tv_datapicker_title = (TextView) view
				.findViewById(R.id.tv_datapicker_title);
		Button mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		// 添加change事件
		id_data.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				// if (wheel == mViewProvince) {
				// updateCities();
				// } else if (wheel == mViewCity) {
				// updateAreas();
				// } else if (wheel == mViewDistrict) {
				// mCurrentDistrictName =
				// mDistrictDatasMap.get(mCurrentCityName)[newValue];
				// mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
				// }
				String result = list.get(newValue);
				tv_datapicker_title.setText(result);
				currPosition = newValue;
			}
		});

		id_data.addClickingListener(new OnWheelClickedListener() {

			@Override
			public void onItemClicked(WheelView wheel, int itemIndex) {
				// TODO Auto-generated method stub
				String result = list.get(itemIndex);
				tv_datapicker_title.setText(result);
				currPosition = itemIndex;
//				if (selectResultListener != null)
//					selectResultListener.onResult(dialog_photo, currPosition);
				id_data.setCurrentItem(currPosition);
			}
		});
		// 添加onclick事件
		mBtnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// showSelectedResult();
				if (selectResultListener != null)
					selectResultListener.onResult(dialog_photo, currPosition);
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
		id_data.setViewAdapter(new ArrayWheelAdapter<String>(context, list));
		// 设置可见条目数量
		id_data.setVisibleItems(7);
		id_data.setCurrentItem(currPostion);
		dialog_photo.show();
	}

	// /**
	// * 根据当前的市，更新区WheelView的信息
	// */
	// private void updateAreas() {
	// int pCurrent = mViewCity.getCurrentItem();
	// mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
	// String[] areas = mDistrictDatasMap.get(mCurrentCityName);
	//
	// if (areas == null) {
	// areas = new String[] { "" };
	// }
	// mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context,
	// areas));
	// mViewDistrict.setCurrentItem(0);
	// }

	// /**
	// * 根据当前的省，更新市WheelView的信息
	// */
	// private void updateCities() {
	// int pCurrent = mViewProvince.getCurrentItem();
	// mCurrentProviceName = mProvinceDatas[pCurrent];
	// String[] cities = mCitisDatasMap.get(mCurrentProviceName);
	// if (cities == null) {
	// cities = new String[] { "" };
	// }
	// mViewCity.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
	// mViewCity.setCurrentItem(0);
	// updateAreas();
	// }

	// @Override
	// public void onClick(View v) {
	// switch (v.getId()) {
	// case R.id.btn_confirm:
	// showSelectedResult();
	// break;
	// case R.id.btn_cancle:
	// dialog_photo.dismiss();
	// dialog_photo = null;
	// break;+
	// default:
	// break;
	// }
	// }

	// private void showSelectedResult() {
	// // Toast.makeText(context,
	// "当前选中:"+mCurrentProviceName+","+mCurrentCityName+","
	// // +mCurrentDistrictName+","+mCurrentZipCode, Toast.LENGTH_SHORT).show();
	// if(address!=null)address.setText(mCurrentCityName);
	// dialog_photo.dismiss();
	// dialog_photo = null;
	// }

}
