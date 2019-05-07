package com.itxiaox.xview.addressDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.util.Xml;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.itxiaox.xview.R;
import com.itxiaox.xview.adapter.CommonAdapter;
import com.itxiaox.xview.adapter.ViewHolder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AddressDialog {

	private Dialog dialog_select;
	private TextView mDialogText;
	private WindowManager mWindowManager;
	private InputMethodManager inputMethodManager;
	private SideBar indexBar;
	private AddressAdapter adapter;
	private ListView mListView;
	private Activity mContext;
	private List<Content> searchList;
	private List<Content> list;
	private AsyncTask<String, Integer, List<Content>> myQueryTask;
	private List<ChooseCityInfo> xmlData;
	private boolean isSearch = false;
	public AddressDialog(Activity context) {
		this.mContext = context;
		searchList = new ArrayList<Content>();
		list = new ArrayList<Content>();
		inputMethodManager = (InputMethodManager) mContext
				.getSystemService(Context.INPUT_METHOD_SERVICE);
	}

	public interface AddressSelectResult{
		public void onResult(Dialog dialog, String result);
		public void onCancel(Dialog dialog);
	}
	private AddressSelectResult addressSelectResult;
	private EditText query;
	public void setAddressSelectResult(AddressSelectResult addressSelectResult){
		this.addressSelectResult = addressSelectResult;
	}
	/**
	 * 下拉列表
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void selectDialog() {
		dialog_select = new Dialog(mContext, R.style.dropdownlist);
		dialog_select.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog_select.setCanceledOnTouchOutside(false);
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View view = inflater.inflate(R.layout.xx_dialog_contact, null);
		Window window = dialog_select.getWindow();
		WindowManager m = window.getWindowManager();
		window.setWindowAnimations(R.style.ActionSheetDialogAnimation); // 添加动画
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		// 重新设置
		LayoutParams lp = window.getAttributes();
		window.setGravity(Gravity.BOTTOM);
		lp.x = 0; // 新位置X坐标
		lp.y = 0; // 新位置Y坐标
		dialog_select.setContentView(view);
		dialog_select.setCancelable(true);
		dialog_select.setCanceledOnTouchOutside(true);
		lp.height = (int) (d.getHeight()-dp2px(mContext, 60)); // 高度设置为屏幕的0.6
		lp.width = window.getAttributes().FILL_PARENT;
		window.setAttributes(lp);

		initListView(view);
		if (!dialog_select.isShowing()) {
			dialog_select.show();
		}

		Button btn_dialogcalendar_sure = (Button) view.findViewById(R.id.btn_dialogcalendar_sure);
		GridView gridview_choosecity = (GridView) view.findViewById(R.id.gridview_choosecity);
		gridview_choosecity.setSelector(new ColorDrawable(Color.TRANSPARENT));
		final List<String> normalCity = new ArrayList<String>();
		normalCity.add("北京");
		normalCity.add("上海");
		normalCity.add("广州");
		normalCity.add("深圳");
		normalCity.add("杭州");
		CommonAdapter<String> normalAdapter= new CommonAdapter<String>(mContext, normalCity, R.layout.xx_gridview_choosecity) {

			@Override
			public void convert(ViewHolder holder,
					String t) {
				// TODO Auto-generated method stub
				holder.setText(R.id.tv_choosecity_gridview, t);
			}
		};

		gridview_choosecity.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				hideSoftKeyboard();
				if(addressSelectResult!=null){
					addressSelectResult.onResult(dialog_select, normalCity.get(position));
				}
			}
		});
		btn_dialogcalendar_sure.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String result = query.getText().toString().trim();
				hideSoftKeyboard();
				dialog_select.dismiss();
				if(!TextUtils.isEmpty(result)){
					if(addressSelectResult!=null){
						addressSelectResult.onResult(dialog_select, result);
					}
				}
				
			}
		});
		gridview_choosecity.setAdapter(normalAdapter);
		
		GetCityTask getCityTask = new GetCityTask();
		getCityTask.execute();
		hideSoftKeyboard();
	}

	private void initListView(View view) {
//		mDialogText = (TextView) LayoutInflater.from(mContext).inflate(
//				R.layout.list_position, null);
		// head = LayoutInflater.from(this).inflate(R.layout.search_bar, null);
		// mListView.addHeaderView(head);
		mDialogText = (TextView) view.findViewById(R.id.tv_dialog_select);
		indexBar = (SideBar) view.findViewById(R.id.sideBar);
		mListView = (ListView) view.findViewById(R.id.list);
		// 搜索框
		query = (EditText) view.findViewById(R.id.query);
		query.setHint("请输入地址");
		final ImageButton clearSearch = (ImageButton) view
				.findViewById(R.id.search_clear);
		query.addTextChangedListener(new TextWatcher() {

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// adapter.getFilter().filter(s);
				if (s.length() > 0) {
					clearSearch.setVisibility(View.VISIBLE);
				} else {
					clearSearch.setVisibility(View.INVISIBLE);

				}

				if (!TextUtils.isEmpty(s)) {
					query.requestFocus();
					myQueryTask = new MyQueryTask();
					myQueryTask.execute(s.toString());
					isSearch = true;
				} else {
					isSearch = false;
					initListViewData(list);
				}
				// adapter.getFilter().filter(s);

			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			public void afterTextChanged(Editable s) {
			}
		});
		clearSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				query.getText().clear();
				hideSoftKeyboard();
			}
		});

		mDialogText.setVisibility(View.INVISIBLE);
//		mWindowManager = (WindowManager) mContext
//				.getSystemService(Context.WINDOW_SERVICE);
//		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
//				WindowManager.LayoutParams.TYPE_APPLICATION,
//				WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//						| WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
//				PixelFormat.TRANSLUCENT);
//		mWindowManager.addView(mDialogText, lp);
		indexBar.setTextView(mDialogText);
		// 初始化数据
		// list = new ArrayList<ContactInfo>();
		// for (int i = 0; i < 10; i++) {
		// ContactInfo m;
		// if (i < 3)
		// m = new ContactInfo("A", "选项" + i);
		// else if (i < 6)
		// m = new ContactInfo("F", "选项" + i);
		// else
		// m = new ContactInfo("D", "选项" + i);
		// list.add(m);
		// }
		// 根据a-z进行排序
		Collections.sort(list, new PinyinComparator());
		// 实例化自定义内容适配类
		adapter = new AddressAdapter(mContext, list);
		// 为listView设置适配
		mListView.setAdapter(adapter);
		// 设置SideBar的ListView内容实现点击a-z中任意一个进行定位
		indexBar.setListView(mListView);


		// 设置右侧触摸监听
		indexBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// 该字母首次出现的位置
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					mListView.setSelection(position);
				}

			}
		});

		// btn_search.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// String key = edit_search.getText().toString().trim();
		// if(TextUtils.isEmpty(key)){
		// CommonUtil.StartToast(ContactListActivity.this, "请先输入内容");
		// return;
		// }
		// // adapter.getFilter().filter(s);
		// List<ContactInfo> searchList = search(key);
		// initListViewData(searchList);
		//
		// }
		// });
	}

	private void initListViewData(final List<Content> list) {
		Collections.sort(list, new PinyinComparator());
		// 实例化自定义内容适配类
		adapter = new AddressAdapter(mContext, list);
		// 为listView设置适配
		mListView.setAdapter(adapter);
		// 设置SideBar的ListView内容实现点击a-z中任意一个进行定位
		indexBar.setListView(mListView);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Content contactInfo = list.get(position);
				hideSoftKeyboard();
				if(addressSelectResult!=null&&contactInfo!=null){
					addressSelectResult.onResult(dialog_select, contactInfo.getName());
				}
			}
		});
	}

	class MyQueryTask extends AsyncTask<String, Integer, List<Content>> {

		@Override
		protected void onPreExecute() {
			// Log.i(TAG, "onPreExecute() called");
			// textView.setText("loading...");
		}

		@Override
		protected List<Content> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<Content> searchList = search(params[0]);
			return searchList;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Integer... progresses) {
			// Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
			// progressBar.setProgress(progresses[0]);
			// textView.setText("loading..." + progresses[0] + "%");
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(List<Content> result) {
			// Log.i(TAG, "onPostExecute(Result result) called");
			// textView.setText(result);
			//
			// execute.setEnabled(true);
			// cancel.setEnabled(false);
			initListViewData(result);
		}

	}

	void hideSoftKeyboard() {
//		if (mContext.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
//			if (mContext.getCurrentFocus() != null)
//				inputMethodManager.hideSoftInputFromWindow(mContext
//						.getCurrentFocus().getWindowToken(),
//						InputMethodManager.HIDE_NOT_ALWAYS);
//		}
		InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);   
        imm.hideSoftInputFromWindow(query.getWindowToken(),0); 
//		closeHideSoftKeyboard();
	}

	private void closeHideSoftKeyboard(){
		InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);  
		   imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);  
	}
	private List<Content> search(String key) {
		searchList.clear();
		for (int i = 0; i < list.size(); i++) {
			Content contactInfo = list.get(i);
			// if(contactInfo.getName().contain(key)){
			// searchList.add(contactInfo);
			// }else if(contactInfo.getLetter().equals(key.toUpperCase())
			// ||((!TextUtils.isEmpty(contactInfo.getName())&&)
			// ||(!TextUtils.isEmpty(contactInfo.getDepartment())&&contactInfo.getDepartment().contains(key)))){
			// // CommonUtil.StartToast(ContactListActivity.this,
			// "查询到："+contactInfo.getName());
			// // mListView.setSelection(i);
			// // flag = true;
			// // break;
			// searchList.add(contactInfo);
			// }

			if (contactInfo.getName().contains(key)) {
				searchList.add(contactInfo);
			}
		}
		return searchList;
	}

	/**
	 * 从xml中解析城市列表并初始化城市数据
	 * */
	private List<ChooseCityInfo> initCityData() {
		List<ChooseCityInfo> mCityData = new ArrayList<ChooseCityInfo>();
		InputStream is;
		try {
			is = mContext.getAssets().open("city.xml");
			XmlPullParser parser = Xml.newPullParser(); // 由android.util.Xml创建一个XmlPullParser实例
			parser.setInput(is, "UTF-8"); // 设置输入流 并指明编码方式
			int eventType = parser.getEventType();
			ChooseCityInfo info = null;
			String tagName = "";
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:

					break;
				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if (tagName.equals("string")) {
						info = new ChooseCityInfo();
					}
					break;
				case XmlPullParser.END_TAG:
					tagName = parser.getName();
					if (tagName.equals("string")) {
						mCityData.add(info);
						info = null;
					}
					break;
				// TEXT代表文本结点事件
				case XmlPullParser.TEXT:
					if (tagName.equals("string")) {
						if (info != null)
							info.setName(parser.getText());
					}
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}
		return mCityData;
	}

	private List<Content> covert() {
		if (xmlData == null || xmlData.size() == 0) {
			xmlData = initCityData();
		}
		List<Content> contactList = new ArrayList<Content>();
		if (xmlData != null && xmlData.size() > 0) {
			for (ChooseCityInfo usersInfo : xmlData) {
				String name = usersInfo.getName();
				String letter = getFristLetter(name);
				Content content = new Content(letter, name);
				contactList.add(content);
			}
		}
		return contactList;
	}

	private String getFristLetter(String chinese) {
		return ChineseCharToEn.getFirstLetter(chinese).toUpperCase();
	}

	class GetCityTask extends AsyncTask<String, Integer, List<Content>> {

		@Override
		protected void onPreExecute() {
			// Log.i(TAG, "onPreExecute() called");
			// textView.setText("loading...");
		}

		@Override
		protected List<Content> doInBackground(String... params) {
			// TODO Auto-generated method stub
			List<Content> data = covert();

			return data;
		}

		// onProgressUpdate方法用于更新进度信息
		@Override
		protected void onProgressUpdate(Integer... progresses) {
			// Log.i(TAG, "onProgressUpdate(Progress... progresses) called");
			// progressBar.setProgress(progresses[0]);
			// textView.setText("loading..." + progresses[0] + "%");
		}

		// onPostExecute方法用于在执行完后台任务后更新UI,显示结果
		@Override
		protected void onPostExecute(List<Content> result) {
			// Log.i(TAG, "onPostExecute(Result result) called");
			// textView.setText(result);
			//
			// execute.setEnabled(true);
			// cancel.setEnabled(false);
			list = result;
			initListViewData(result);
		}

	}
	/**
	 * dp 转 px
	 * @param dpVal dp单位
	 * @return
	 */
	private  int dp2px(Context context,float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, context.getResources().getDisplayMetrics());
	}

}
