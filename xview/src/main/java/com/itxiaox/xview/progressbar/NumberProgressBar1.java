package com.itxiaox.xview.progressbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.ProgressBar;
  
/**
 * 带文字的进度的进度条
 * @ClassName: NumberProgress 
 * @Description: TODO
 * @author xiaoxiao
 * @date 2015-5-14 下午5:58:28 
 *
 */
public class NumberProgressBar1 extends ProgressBar{
        String text;
        Paint mPaint;
         
        public NumberProgressBar1(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
            System.out.println("1");
            initText();
        }
         
        public NumberProgressBar1(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            // TODO Auto-generated constructor stub
            System.out.println("2");
            initText();
        }
     
     
        public NumberProgressBar1(Context context, AttributeSet attrs) {
            super(context, attrs);
            // TODO Auto-generated constructor stub
            System.out.println("3");
            initText();
        }
         
        @Override
        public synchronized void setProgress(int progress) {
            // TODO Auto-generated method stub
            setText(progress);
            super.setProgress(progress);
             
        }
     
        @Override
        protected synchronized void onDraw(Canvas canvas) {
            // TODO Auto-generated method stub
            super.onDraw(canvas);
            //this.setText();
            Rect rect = new Rect();
            this.mPaint.getTextBounds(this.text, 0, this.text.length(), rect);
            int x = (getWidth() / 2) - rect.centerX(); 
            int y = (getHeight() / 2) - rect.centerY(); 
            canvas.drawText(this.text, x, y, this.mPaint); 
        }
         
        //初始化，画笔
        private void initText(){
            this.mPaint = new Paint();
            this.mPaint.setColor(Color.WHITE);
            //在原作者的基础上增加了以下代码，因为在高分辨率的手机下，进度条里的字体太小
            DisplayMetrics dm = getResources().getDisplayMetrics();
            int displayWidth = dm.widthPixels;
            if(displayWidth>480)
            {
            	this.mPaint.setTextSize(36);
            }
            else
            { 
            	this.mPaint.setTextSize(18);        	
            }
             
        }
         
        private void setText(){
            setText(this.getProgress());
        }
         
        //设置文字内容
        private void setText(int progress){
            int i = (progress * 100)/this.getMax();
            this.text = String.valueOf(i) + "%";
        }
         
		/**
	     * @Description: TODO
	     * @param context
	     * @return
	     * @return float
	     */
	    public static int getScreenWidth(Activity context) {
	        DisplayMetrics metric = new DisplayMetrics();
	        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
	        return metric.widthPixels;
	    }

	    /**
	     * @Description: TODO
	     * @param context
	     * @return
	     * @return float
	     */
	    public static int getScreenHeight(Activity context) {
	        DisplayMetrics metric = new DisplayMetrics();
	        context.getWindowManager().getDefaultDisplay().getMetrics(metric);
	        return metric.heightPixels;
	    }

}