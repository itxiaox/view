package com.github.itxiaox.address.address;




import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.widget.TextView;

public class ItemSelectPicker {
	private Context context;
	private Dialog dialog;
	private TextView txt_title;
	private Display display;
	
	protected void selectDialog(Context context,TextView textviwe) {
		this.context = context; 
	}
}
