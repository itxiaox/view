package com.itxiaox.xview.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebViewClient;

import com.itxiaox.xview.R;


public class ViewActivity extends AppCompatActivity {

    com.itxiaox.xview.databinding.ActivityViewBinding viewBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = com.itxiaox.xview.databinding.ActivityViewBinding.inflate(getLayoutInflater());
        setContentView(viewBinding.getRoot());
        setImage();
        setWebView();
    }

    private void setWebView(){
        viewBinding.ldWebView.setWebViewClient(new WebViewClient());
        viewBinding.ldWebView.loadUrl("https://www.baidu.com");
    }

    public void switchButton(View view) {
        startActivity(new Intent(ViewActivity.this,MyToggleButtonActivity.class));
    }

    public void setImage(){
        viewBinding.textImg.setImageResource(R.drawable.ic_banner);
        viewBinding.textImg.setTextColor(Color.RED);
        viewBinding.textImg.setTextView("首页");
    }
}