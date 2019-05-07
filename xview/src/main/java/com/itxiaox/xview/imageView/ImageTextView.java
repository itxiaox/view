package com.itxiaox.xview.imageView;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.itxiaox.xview.R;

/**
 * 自定义图文控件 Created by xx on 2018/06/22.
 * 参考博客：https://blog.csdn.net/luofen521/article/details/50383191
 *
 */
public class ImageTextView extends LinearLayout {
    private Context context;
    private LinearLayout lL_content;    //包裹图片和标题文字的线性布局，用于改变布局方向
    private ImageView imageView; // 图片
    private TextView textView; // 标题文字
    private ImageView renewalImg; // 更新图片 (eg:通知提示) 注：更新图片和更新文字，只能二选一
    private TextView renewalTv; // 更新内容 （eg:购物车右上的数字）
    @android.support.annotation.IdRes
    int IMAGEVIEW_ID; // 图片的id，用于与右边的图片绑定

    /**
     * ImageView构造方法,用于在Java代码里创建ImageTextView对象
     *
     * @param context  上下文
     * @param vertical LinearLayout是否为垂直方向布局 true-垂直；false-水平
     */
    public ImageTextView(Context context, boolean vertical) {
        super(context);
        this.context = context;
        if (vertical) {
            setOrientation(LinearLayout.VERTICAL);
        } else {
            setOrientation(LinearLayout.HORIZONTAL);
        }
        // 设置权重
        setGravity(Gravity.CENTER);
        // 创建相对布局，用于包裹图片和图片右上冒泡图片
        RelativeLayout relativeLayout = new RelativeLayout(context);
        this.addView(relativeLayout);
        // 图片
        imageView = new ImageView(context);
        RelativeLayout.LayoutParams imageLP = new RelativeLayout.LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        imageLP.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(imageLP);
        imageView.setId(IMAGEVIEW_ID); // 为renewalImg绑定在其
        relativeLayout.addView(imageView);
        // 右上图片
        renewalImg = new ImageView(context);
        RelativeLayout.LayoutParams renewalImgLP = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        renewalImgLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        renewalImgLP.addRule(RelativeLayout.RIGHT_OF, IMAGEVIEW_ID); // 把该图片绑定在id为1的右边
        renewalImg.setLayoutParams(renewalImgLP);
        renewalImg.setBackgroundResource(R.drawable.xx_icon_dot);
        renewalImg.setVisibility(View.GONE); // 默认为隐藏
        relativeLayout.addView(renewalImg);
        // 右上文字
        renewalTv = new TextView(context);
        RelativeLayout.LayoutParams renewalTvLP = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        renewalTvLP.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        renewalTvLP.addRule(RelativeLayout.RIGHT_OF, IMAGEVIEW_ID); // 把该图片绑定在id为1的右边
        renewalTv.setLayoutParams(renewalTvLP);
        renewalTv.setBackgroundResource(R.drawable.xx_icon_dot);
        renewalTv.setVisibility(View.GONE); // 默认为隐藏
        renewalTv.setGravity(Gravity.CENTER);
        renewalTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 8);
        renewalTv.setTextColor(Color.WHITE);
        relativeLayout.addView(renewalTv);
        // 文字
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12);
        this.addView(textView); // 把textView加到ImageTextView里面，即加到图片和右边图片包裹起来的相对布局的右边或者下面
    }

    /**
     * 构造方法：用于在布局xml里面引用ImageTextView
     *
     * @param context
     * @param attrs
     */
    public ImageTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(
                R.layout.xx_imagetextview, this, true);
        lL_content = (LinearLayout) view.findViewById(R.id.lL_content);
        imageView = (ImageView) view.findViewById(R.id.image);
        renewalImg = (ImageView) view.findViewById(R.id.renewalImg);
        renewalTv = (TextView) view.findViewById(R.id.renewalTv);
        textView = (TextView) view.findViewById(R.id.text);

        // 如果xml中的ImageViewText控件中的属性不为空，则取出其属性，并赋值到相应控件上
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs,
                    R.styleable.ImageTextView);
            //方向 0-水平；1-垂直。默认为1
            int orientation = typedArray.getInt(R.styleable.ImageTextView_android_orientation, 1);
            if (orientation == LinearLayout.HORIZONTAL) {    //水平布局
                lL_content.setOrientation(LinearLayout.HORIZONTAL);

            } else {    //垂直
                lL_content.setOrientation(LinearLayout.VERTICAL);
            }
            // textview内容
            CharSequence text = typedArray
                    .getText(R.styleable.ImageTextView_android_text);
            if (text != null) {
                textView.setText(text);
            }
            // textview字体颜色
            ColorStateList color = typedArray.getColorStateList(0);
            if (color != null) {
                textView.setTextColor(color);
            }
            // textview字体大小
            float testSize = typedArray.getFloat(
                    R.styleable.ImageTextView_android_textSize, 12);
            if (testSize != 0) {
                textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, testSize);
            }
            // imageview的图片资源
            Drawable drawable = typedArray
                    .getDrawable(R.styleable.ImageTextView_android_src);
            if (drawable != null) {
                imageView.setImageDrawable(drawable);
            }
            // imageview背景
            Drawable drawableBg = typedArray
                    .getDrawable(R.styleable.ImageTextView_android_background);
            if (drawableBg != null) {
                imageView.setBackgroundDrawable(drawableBg);
            }
            // 保持以后使用属性的一致性
            typedArray.recycle();
        }
        // 对ImageTextView触摸事件的监听
        this.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    imageView.setPressed(true);
                    textView.setPressed(true);
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL
                        || event.getAction() == MotionEvent.ACTION_UP) {
                    imageView.setPressed(false);
                    textView.setPressed(false);
                }
                return false;
            }
        });
    }

    /**
     * 传递Drawable对象设置ImageView图片资源
     *
     * @param drawable
     */
    public void setImageDrawable(Drawable drawable) {
        if (drawable != null) {
            imageView.setImageDrawable(drawable);
        }
    }

    /**
     * 传递ResourceId来设置ImageView的图片资源
     *
     * @param resId
     */
    public void setImageResource(int resId) {
        imageView.setImageResource(resId);
    }

    /**
     * 传递Bitmap对象设置ImageView的图片资源
     *
     * @param bitmap
     */
    public void setImageBitmap(Bitmap bitmap) {
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    /**
     * 得到ImageTextView中的ImageView对象
     *
     * @return
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * 得到ImageView的图片资源
     *
     * @return Drawable对象
     */
    public Drawable getDrawable() {
        return imageView.getDrawable();
    }

    /**
     * 得到ImageView的背景图片
     *
     * @return Drawable对象
     */
    public Drawable getBackground() {
        return imageView.getBackground();
    }

    /**
     * 显示ImageView控件
     */
    public void showImageView() {
        if (imageView.getVisibility() != View.VISIBLE) {
            imageView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏ImageView控件
     */
    public void hideImageView() {
        if (imageView.getVisibility() != View.GONE) {
            imageView.setVisibility(View.GONE);
        }
    }

    /**
     * 传递Drawable对象设置ImageView的背景图片
     *
     * @param drawable
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void setBackgroundDrawable(Drawable drawable) {
        if (drawable != null) {
            imageView.setBackground(drawable);
        }
    }

    /**
     * 传递ResourceId设置ImageView的背景图片
     *
     * @param resId
     */
    public void setBackgroundResource(int resId) {
        imageView.setBackgroundResource(resId);
    }

    /**
     * 设置右上图标是否可见
     *
     * @param visible 右上图标是否可见:true-可见；false-隐藏
     */
    public void setRenewalImgVisible(boolean visible) {
        if (visible) {
            if (renewalImg.getVisibility() != View.VISIBLE) {
                renewalImg.setVisibility(View.VISIBLE);
            }
        } else {
            if (renewalImg.getVisibility() != View.GONE) {
                renewalImg.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置右上文字内容
     *
     * @param text
     */
    public void setRenewalTv(String text) {
        if (text != null) {
            renewalTv.setText(text);
        }
    }

    /**
     * 得到右上TextView控件
     *
     * @return
     */
    public TextView getRenewalTextView() {
        return renewalTv;
    }

    /**
     * 得到右上文字内容
     *
     * @return
     */
    public String getRenewalTv() {
        return renewalTv.getText().toString();
    }

    /**
     * 设置TextView的文字
     *
     * @param text
     */
    public void setTextView(String text) {
        if (text != null) {
            textView.setText(text);
        }
    }

    /**
     * 设置右上文字是否可见
     *
     * @param visible 右上图标是否可见:true-可见；false-隐藏
     */
    public void setRenewalITvVisible(boolean visible) {
        if (visible) {
            if (renewalTv.getVisibility() != View.VISIBLE) {
                renewalTv.setVisibility(View.VISIBLE);
            }
        } else {
            if (renewalTv.getVisibility() != View.GONE) {
                renewalTv.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置TextView的文字大小,默认单位为dip
     *
     * @param size 文字大小
     */
    public void setTestSize(float size) {
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size);
    }

    /**
     * 带单位类型设置TextView的文字大小,默认单位为dip
     *
     * @param size     文字大小
     * @param unitType 单位类型,为TypedValue中的常量。eg:dip->TypedValue.COMPLEX_UNIT_DIP
     */
    public void setTestSize(float size, int unitType) {
        textView.setTextSize(unitType, size);
    }

    /**
     * 设置TextView文字颜色
     *
     * @param color
     */
    public void setTextColor(int color) {
        textView.setTextColor(color);
    }

    /**
     * 设置TextView的Padding
     *
     * @param leftDip
     * @param topDip
     * @param rightDip
     * @param bottomDip
     */
    public void setTextPadding(int leftDip, int topDip, int rightDip,
                               int bottomDip) {
        int leftPx = dip2px(context, leftDip);
        int topPx = dip2px(context, topDip);
        int rightPx = dip2px(context, rightDip);
        int bottomPx = dip2px(context, bottomDip);
        textView.setPadding(leftPx, topPx, rightPx, bottomPx);
    }

    /**
     * 得到ImageTextView中的TextView控件
     *
     * @return
     */
    public TextView getTextView() {
        return textView;
    }

    /**
     * 得到TextView的文字
     *
     * @return
     */
    public String getText() {
        return textView.getText().toString();
    }

    /**
     * 设置TextView单行显示
     */
    public void setSingleLine() {
        textView.setSingleLine();
    }

    /**
     * 根据手机的分辨率从dip单位转成px（像素）
     *
     * @param context
     * @param dipValue
     * @return
     */
    private int dip2px(Context context, float dipValue) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float scale = displayMetrics.density;
        return (int) (dipValue * scale + 0.5f);
    }

}