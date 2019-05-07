package com.itxiaox.xview.gridview.stickyheadergridview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.itxiaox.xview.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.TimeZone;


public class IOSAlbumActivity extends Activity {
	private ProgressDialog mProgressDialog;
	private ImageScanner mScanner;
	private GridView mGridView;
	private List<GridItem> mGirdList = new ArrayList<GridItem>();
	private static int section = 1;
	private Map<String, Integer> sectionMap = new HashMap<String, Integer>();

	private TextView tv_titlebar_right;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xx_activity_iosalbum);
		tv_titlebar_right = (TextView) findViewById(R.id.tv_titlebar_right);
		mGridView = (GridView) findViewById(R.id.asset_grid);
		tv_titlebar_right.setText("仿IOS相册");
		mScanner = new ImageScanner(this);

		mScanner.scanImages(new ImageScanner.ScanCompleteCallBack() {
			{
				mProgressDialog = ProgressDialog.show(IOSAlbumActivity.this, null,
						"正在加载...");
			}

			@Override
			public void scanComplete(Cursor cursor) {
				// 关闭进度条
				mProgressDialog.dismiss();

				while (cursor.moveToNext()) {
					// 获取图片的路径
					String path = cursor.getString(cursor
							.getColumnIndex(MediaStore.Images.Media.DATA));
					long times = cursor.getLong(cursor
							.getColumnIndex(MediaStore.Images.Media.DATE_ADDED));

					GridItem mGridItem = new GridItem(path,
							paserTimeToYM(times));
					mGirdList.add(mGridItem);

				}
				cursor.close();
				Collections.sort(mGirdList, new YMComparator());

				for (ListIterator<GridItem> it = mGirdList.listIterator(); it
						.hasNext();) {
					GridItem mGridItem = it.next();
					String ym = mGridItem.getTime();
					if (!sectionMap.containsKey(ym)) {
						mGridItem.setSection(section);
						sectionMap.put(ym, section);
						section++;
					} else {
						mGridItem.setSection(sectionMap.get(ym));
					}
				}

				mGridView.setAdapter(new StickyGridAdapter(IOSAlbumActivity.this,
						mGirdList, mGridView));

			}
		});

		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				Toast.makeText(IOSAlbumActivity.this, "position=" + position, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	public static String paserTimeToYM(long time) {
		System.setProperty("user.timezone", "Asia/Shanghai");
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
		TimeZone.setDefault(tz);
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		return format.format(new Date(time * 1000L));
	}

}
