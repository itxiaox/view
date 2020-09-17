package com.itxiaox.xview.java.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.itxiaox.xview.R;


/**
 * Created by xiao on 2017/8/31.
 */

public class BatteryView extends View {
    private Paint paint;
    private Paint paint2;
    private RectF rect;
    private Rect rect2;
    private RectF rect3;
    private int battery_width;
    private int battery_inside_margin;
    private int mPower = 100;

    public BatteryView(Context context) {
        super(context);
    }

    public BatteryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.BatteryView);
        int battery_left = (int) a.getDimension(R.styleable.BatteryView_battery_left, 0f);
        int battery_top = (int) a.getDimension(R.styleable.BatteryView_battery_top, 0f);
        battery_width = (int) a.getDimension(R.styleable.BatteryView_battery_width, 25f);
        int battery_height = (int) a.getDimension(R.styleable.BatteryView_battery_height, 25f);
        int battery_head_width = (int) a.getDimension(R.styleable.BatteryView_battery_head_width, 10f);
        int battery_head_height = (int) a.getDimension(R.styleable.BatteryView_battery_head_height, 10f);
        battery_inside_margin = (int) a.getDimension(R.styleable.BatteryView_battery_inside_margin, 5f);
        int battery_stroke_width = (int) a.getDimension(R.styleable.BatteryView_battery_stroke_width, 5f);
        int paintColor = a.getColor(R.styleable.BatteryView_battery_color,
                getResources().getColor(R.color.font_grey));
        a.recycle();
        //先画外框
        paint = new Paint();
        paint.setColor(paintColor);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(battery_stroke_width);
        paint.setStrokeCap(Paint.Cap.ROUND);
        rect = new RectF(battery_left, battery_top,
                battery_left + battery_width, battery_top + battery_height);

        paint2 = new Paint(paint);
        paint2.setStyle(Paint.Style.FILL);

        //画电池头
        int h_left = battery_left + battery_width;
        int h_top = battery_top + battery_height / 2 - battery_head_height / 2;
        int h_right = h_left + battery_head_width;
        int h_bottom = h_top + battery_head_height;
        rect3 = new RectF(h_left, h_top, h_right, h_bottom);

        rect2 = new Rect();
        rect2.left = battery_left + battery_inside_margin;
        rect2.top = battery_top + battery_inside_margin;
        rect2.bottom = rect2.top + battery_height - battery_inside_margin * 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(rect, 3, 3, paint);
        float power_percent = mPower / 100.0f;
        //画电量
        if (power_percent != 0) {
            rect2.right = rect2.left - battery_inside_margin + (int) ((battery_width - battery_inside_margin) * power_percent);
            canvas.drawRect(rect2, paint2);
        }
        //画电池头
        canvas.drawRect(rect3, paint2);
        canvas.drawRoundRect(rect3, 3, 3, paint);
        //画电量数字
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.BLACK);
//        paint.setStrokeWidth(0.5f);
//        paint.setTextSize(4*battery_height/5);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
//        canvas.drawText(String.valueOf(mPower) + "",(battery_left+battery_left + battery_width)/2-paint.measureText(mPower+"")/2, h_bottom, paint);
    }

    public void setPower(int power) {
        mPower = power;
        if (mPower < 0) {
            mPower = 0;
        }
        invalidate();
    }
}