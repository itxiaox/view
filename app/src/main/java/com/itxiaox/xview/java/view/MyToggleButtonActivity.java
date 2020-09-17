package com.itxiaox.xview.java.view;//package com.itxiaox.xxview.switchButton;

import com.itxiaox.xview.R;
import com.itxiaox.xview.switchButton.MyToggleButton;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MyToggleButtonActivity extends Activity {
	private final String TAG = "MainActivity";
	private MyToggleButton myToggleButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xx_activity_mytogglebutton);

		myToggleButton = (MyToggleButton) this.findViewById(R.id.myToggleButton);
		myToggleButton.setState(true);//设置默认状态
		myToggleButton.setOnToggleButtonClickListener(new MyToggleButton.ToggleButtonCallBackListener() {

			@Override
			public void chanage(boolean isCheck) {
				// TODO Auto-generated method stub
				Log.v(TAG, "isCheck="+isCheck);
				if(isCheck){
					Toast.makeText(MyToggleButtonActivity.this, "打开", Toast.LENGTH_SHORT).show();
				}else{
					Toast.makeText(MyToggleButtonActivity.this, "关闭", Toast.LENGTH_SHORT).show();
				}
			}
		});
		Log.v(TAG, "getChecked="+myToggleButton.getState());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
