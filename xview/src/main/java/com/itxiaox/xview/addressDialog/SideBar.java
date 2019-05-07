package com.itxiaox.xview.addressDialog;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.itxiaox.xview.R;

public class SideBar extends View {
	
	private char[] l;
	private SectionIndexer sectionIndexter = null;
	private ListView list;
	private TextView mDialogText;
//	Bitmap mbitmap;
	private int type = 1;
	private int color = Color.BLACK;
	private int fontSize ; //索引字体大小，索引字颜色
	public SideBar(Context context) {
		super(context);
		init(context);
	}

	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		fontSize = dp2px(context, 10);
		l = new char[] { '!','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
		 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
		  'W', 'X', 'Y', 'Z','#'};
//		mbitmap = BitmapFactory.decodeResource(getResources(),
//				R.drawable.scroll_bar_search_icon);
	}

	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}


	public void setListView(ListView _list) {
		list = _list;
//		HeaderViewListAdapter ha = (HeaderViewListAdapter) _list
//				.getAdapter();
		sectionIndexter = (SectionIndexer) _list.getAdapter();
		
	}

	public void setTextView(TextView mDialogText) {
		this.mDialogText = mDialogText;
	}

	public boolean onTouchEvent(MotionEvent event) {

		super.onTouchEvent(event);
		int i = (int) event.getY();
		final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
		int idx = i / (getMeasuredHeight() / l.length);
		if (idx >= l.length) {
			idx = l.length - 1;
		} else if (idx < 0) {
			idx = 0;
		}
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			setBackgroundResource(R.drawable.xx_scrollbar_bg);
			if(mDialogText!=null){
				mDialogText.setVisibility(View.VISIBLE);
				if (idx == 0) {
					mDialogText.setText("Search");
					mDialogText.setTextSize(16);
				} else {
					mDialogText.setText(String.valueOf(l[idx]));
					mDialogText.setTextSize(34);
				}
			}
			if (sectionIndexter == null) {
				sectionIndexter = (SectionIndexer) list.getAdapter();
			}
			int position = sectionIndexter.getPositionForSection(l[idx]);

			if (position == -1) {
				return true;
			}
			list.setSelection(position);
		} else {
			if(mDialogText!=null){
			mDialogText.setVisibility(View.INVISIBLE);
			}

		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			setBackgroundDrawable(new ColorDrawable(0x00000000));
		}
		return true;
	}

	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setTextSize(fontSize);
		paint.setStyle(Style.FILL);		
		paint.setTextAlign(Paint.Align.CENTER);
		float widthCenter = getMeasuredWidth() / 2;
		if (l.length > 0) {
			float height = getMeasuredHeight() / l.length;
			for (int i = 0; i < l.length; i++) {
				if (i == 0 && type != 2) {
//					canvas.drawBitmap(mbitmap, widthCenter - 7, (i + 1)
//							* height - height / 2, paint);
				} else
					canvas.drawText(String.valueOf(l[i]), widthCenter,
							(i + 1) * height, paint);
			}
		}
		this.invalidate();
		super.onDraw(canvas);
	}
	// 触摸事件
			private OnTouchingLetterChangedListener	onTouchingLetterChangedListener;
	/**
//	 * 向外公开的方法
//	 * 
//	 * @param onTouchingLetterChangedListener
//	 */
	public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
		this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
	}

	/**
	 * 接口
	 * 
	 * @author coder
	 * 
	 */
	public interface OnTouchingLetterChangedListener {
		public void onTouchingLetterChanged(String s);
	}

	/**
	 * dp 转 px
	 * @param dpVal dp单位
	 * @return
	 */
	private   int dp2px(Context context,float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, context.getResources().getDisplayMetrics());
	}
}
