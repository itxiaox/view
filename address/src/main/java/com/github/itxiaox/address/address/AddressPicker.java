package com.github.itxiaox.address.address;

import android.app.Dialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;


import com.github.itxiaox.address.R;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class AddressPicker implements OnWheelChangedListener {

	/**
	 * 所有省
	 */
	protected String[] mProvinceDatas;
	private String[] cities;
	private String[] areas;
	/**
	 * key - 省 value - 市
	 */
	protected Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
	/**
	 * key - 市 values - 区
	 */
	protected Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

	/**
	 * key - 区 values - 邮编
	 */
	protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

	/**
	 * 当前省的名称
	 */
	protected String mCurrentProviceName;
	/**
	 * 当前市的名称
	 */
	protected String mCurrentCityName;
	/**
	 * 当前区的名称
	 */
	protected String mCurrentDistrictName = "";
	/**
	 * 默认省的名称
	 */
	protected String defaultProvice;
	/**
	 * 默认市的名称
	 */
	protected String defaultCity;
	/**
	 * 默认区的名称
	 */
	protected String defaultDistrict = "";

	/**
	 * 当前区的邮政编码
	 */
	protected String mCurrentZipCode = "";
	private Dialog dialog;
	private View view;
	private Button mBtnConfirm;
	private Button btn_cancle;;
	private WheelView mViewProvince;
	private TextView tv_addresspicker_title;
	public static int Mode_ProviceAndCity = 1;// 省市二级
	public static int Mode_All = 0;// 省市县三级
	private int mode;
	private static AddressPicker addressPicker;
	private String dataformatStr;

	public AddressPicker() {
	}

	public static AddressPicker getInstance() {
		if (addressPicker == null) {
			addressPicker = new AddressPicker();
		}
		return addressPicker;
	}

	public interface SelectResultListener {
		public void onResult(Dialog dialog, String dataformatStr,
                             String Provice, String city, String district);
	}

	public AddressPicker setDefaultAdress(String priovice, String city,
			String district) {
		if (!TextUtils.isEmpty(priovice)) {
			defaultProvice = priovice;
		}
		if (!TextUtils.isEmpty(city)) {
			defaultCity = city;
		}
		if (!TextUtils.isEmpty(district)) {
			defaultDistrict = district;
		}
		return addressPicker;
	}

	/**
	 * 照片选择Dialog
	 */
	public void selectAddressDialog(Context context, int showMode,
			final SelectResultListener selectResultListener) {
		this.context = context;
		dialog = new Dialog(context, R.style.ActionSheetDialogStyle);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = LayoutInflater.from(context);
		view = inflater.inflate(R.layout.xx_activity_addresspicker, null);
		tv_addresspicker_title = (TextView) view
				.findViewById(R.id.tv_addresspicker_title);
		Window window = dialog.getWindow();
		// 重新设置
		WindowManager.LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		window.setWindowAnimations(R.style.ActionSheetDialogAnimation); // 添加动画
		window.setAttributes(lp);
		dialog.setContentView(view);
		/*
		 * 将对话框的大小按屏幕大小的百分比设置
		 */
		WindowManager m = window.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = window.getAttributes(); // 获取对话框当前的参数值
		// p.height = (int) (d.getHeight() * 0.4); // 高度设置为屏幕的0.6
		p.height = lp.WRAP_CONTENT;
		p.width = (int) (d.getWidth() * 1.0); // 宽度设置为屏幕的0.65
		window.setAttributes(p);
		lp.x = 0; // 新位置X坐标
		lp.y = 0; // 新位置Y坐标
		mViewProvince = (WheelView) view.findViewById(R.id.id_province);
		mViewCity = (WheelView) view.findViewById(R.id.id_city);
		mViewDistrict = (WheelView) view.findViewById(R.id.id_district);
		mBtnConfirm = (Button) view.findViewById(R.id.btn_confirm);
		btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
		mode = showMode;
		switch (mode) {
		case 0:
			mViewDistrict.setVisibility(View.VISIBLE);// 省市县三级
			break;
		case 1:
			mViewDistrict.setVisibility(View.GONE);// 隐藏县级市，只有省和市
			break;
		default:
			break;
		}
		// mViewDistrict.setVisibility(View.GONE);//隐藏县级市，只有省和市
		// 添加change事件
		mViewProvince.addChangingListener(this);
		// 添加change事件
		mViewCity.addChangingListener(this);
		// 添加change事件
		mViewDistrict.addChangingListener(this);
		// 添加onclick事件
		mBtnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (selectResultListener != null) {
					selectResultListener.onResult(dialog, dataformatStr,
							mCurrentProviceName, mCurrentCityName,
							mCurrentDistrictName);
				}
			}
		});
		btn_cancle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				dialog = null;
			}
		});
		setUpData();
		dialog.show();
	}

	private Context context;
	private WheelView mViewDistrict;
	private WheelView mViewCity;
	

	private void setUpData() {
		initProvinceDatas();
		mViewProvince.setViewAdapter(new ArrayWheelAdapter<String>(context,
				mProvinceDatas));
		// 设置可见条目数量
		mViewProvince.setVisibleItems(7);
		mViewCity.setVisibleItems(7);
		mViewDistrict.setVisibleItems(7);
		updateCities();
		updateAreas();
		mViewProvince.setCurrentItem(getProvincePosition(defaultProvice));
		mViewCity.setCurrentItem(getCityPosition(defaultCity));
		mViewDistrict.setCurrentItem(getDistrictPosition(defaultDistrict));
//		mViewProvince.setCurrentItem(2);
//		mViewCity.setCurrentItem(2);
//		mViewDistrict.setCurrentItem(2);
	}
	
	private int getProvincePosition(String province){
		for (int i = 0; i < mProvinceDatas.length; i++) {
			if(mProvinceDatas[i].equals(province)){
				return i;
			}
		}
		return 0;
	}
	private int getCityPosition(String cityName){
		for (int i = 0; i < cities.length; i++) {
			if(cities[i].equals(cityName)){
				return i;
			}
		}
		return 0;
	}
	private int getDistrictPosition(String distruct){
		for (int i = 0; i < areas.length; i++) {
			if(areas[i].equals(distruct)){
				return i;
			}
		}
		return 0;
	}
	

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == mViewProvince) {
			updateCities();
		} else if (wheel == mViewCity) {
			updateAreas();
		} else if (wheel == mViewDistrict) {
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[newValue];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
		switch (mode) {
		case 0:
			dataformatStr = mCurrentProviceName + "-" + mCurrentCityName + "-"
					+ mCurrentDistrictName;
			tv_addresspicker_title.setText(dataformatStr);
			break;
		case 1:
			dataformatStr = mCurrentProviceName + "-" + mCurrentCityName;
			tv_addresspicker_title.setText(mCurrentProviceName + "-"
					+ mCurrentCityName);
			break;
		default:
			break;
		}

	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas() {
		int pCurrent = mViewCity.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		 areas = mDistrictDatasMap.get(mCurrentCityName);

		if (areas == null) {
			areas = new String[] { "" };
		}
		mViewDistrict.setViewAdapter(new ArrayWheelAdapter<String>(context,
				areas));
		mViewDistrict.setCurrentItem(0);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities() {
		int pCurrent = mViewProvince.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];
		 cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		mViewCity
				.setViewAdapter(new ArrayWheelAdapter<String>(context, cities));
		mViewCity.setCurrentItem(0);
		updateAreas();
	}

	/**
	 * 解析省市区的XML数据
	 */

	protected void initProvinceDatas() {
		List<ProvinceModel> provinceList = null;
		AssetManager asset = context.getAssets();
		try {
			InputStream input = asset.open("province_data.xml");
			// 创建一个解析xml的工厂对象
			SAXParserFactory spf = SAXParserFactory.newInstance();
			// 解析xml
			SAXParser parser = spf.newSAXParser();
			XmlParserHandler handler = new XmlParserHandler();
			parser.parse(input, handler);
			input.close();
			// 获取解析出来的数据
			provinceList = handler.getDataList();
			// */ 初始化默认选中的省、市、区
			if (provinceList != null && !provinceList.isEmpty()) {
				mCurrentProviceName = provinceList.get(0).getName();
				List<CityModel> cityList = provinceList.get(0).getCityList();
				if (cityList != null && !cityList.isEmpty()) {
					mCurrentCityName = cityList.get(0).getName();
					List<DistrictModel> districtList = cityList.get(0)
							.getDistrictList();
					mCurrentDistrictName = districtList.get(0).getName();
					mCurrentZipCode = districtList.get(0).getZipcode();
				}
			}
			// */
			mProvinceDatas = new String[provinceList.size()];
			for (int i = 0; i < provinceList.size(); i++) {
				// 遍历所有省的数据
				mProvinceDatas[i] = provinceList.get(i).getName();
				List<CityModel> cityList = provinceList.get(i).getCityList();
				String[] cityNames = new String[cityList.size()];
				for (int j = 0; j < cityList.size(); j++) {
					// 遍历省下面的所有市的数据
					cityNames[j] = cityList.get(j).getName();
					List<DistrictModel> districtList = cityList.get(j)
							.getDistrictList();
					String[] distrinctNameArray = new String[districtList
							.size()];
					DistrictModel[] distrinctArray = new DistrictModel[districtList
							.size()];
					for (int k = 0; k < districtList.size(); k++) {
						// 遍历市下面所有区/县的数据
						DistrictModel districtModel = new DistrictModel(
								districtList.get(k).getName(), districtList
										.get(k).getZipcode());
						// 区/县对于的邮编，保存到mZipcodeDatasMap
						mZipcodeDatasMap.put(districtList.get(k).getName(),
								districtList.get(k).getZipcode());
						distrinctArray[k] = districtModel;
						distrinctNameArray[k] = districtModel.getName();
					}
					// 市-区/县的数据，保存到mDistrictDatasMap
					mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
				}
				// 省-市的数据，保存到mCitisDatasMap
				mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {

		}
	}
}
