package com.github.itxiaox.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

/**
 * 黑色主题
 */
public class CommonDialogFragment extends DialogFragment implements View.OnClickListener{

    private Context mContext;
    private String content;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view = inflater.inflate(R.layout.dialog_commom, container);
        initView();
        return view;
    }


    /**
     * 设置返回键监听
     * <pre>
     *      //点击dialog之外不消失
     *         dialogFragment.setCancelable(false);
     *      //点击返回键不消失
     *               dialogFragment.setOnKeyListener((dialog, keyCode, event) -> {
     *                 if(keyCode == KeyEvent.KEYCODE_BACK){
     *                      return true;
     *                   }
     *                  return false;
     *               });
     * <pre/>
     *
     * @param onKeyListener
     */
    public void setOnKeyListener(DialogInterface.OnKeyListener onKeyListener){
        //点击返回键不消失
        if( getDialog()!=null)
        getDialog().setOnKeyListener(onKeyListener);
    }

    public CommonDialogFragment setOnCloseListener(OnCloseListener listener){
        this.listener = listener;
        return this;
    }

    public CommonDialogFragment setTitle(String title){
        this.title = title;
        return this;
    }

    public CommonDialogFragment setContent(String content){
        this.content = content;
        return this;
    }
    public CommonDialogFragment setPositiveButton(String name){
        this.positiveName = name;
        return this;
    }

    public CommonDialogFragment setNegativeButton(String name){
        this.negativeName = name;
        return this;
    }


    private void initView(){
        TextView contentTxt = view.findViewById(R.id.content);
        TextView titleTxt = view.findViewById(R.id.title);
        TextView submitTxt = view.findViewById(R.id.submit);
        submitTxt.setOnClickListener(this);
        TextView cancelTxt = view.findViewById(R.id.cancel);
        cancelTxt.setOnClickListener(this);

        contentTxt.setText(content);
        if(!TextUtils.isEmpty(positiveName)){
            submitTxt.setText(positiveName);
        }

        if(!TextUtils.isEmpty(negativeName)){
            cancelTxt.setText(negativeName);
        }

        if(!TextUtils.isEmpty(title)){
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.cancel) {
            if (listener != null) {
                listener.onClick(this, false);
            }
            this.dismiss();

        } else if (i == R.id.submit) {
            if (listener != null) {
                listener.onClick(this, true);
            }

        }
    }

    public interface OnCloseListener{
        void onClick(DialogFragment dialog, boolean confirm);
    }

}