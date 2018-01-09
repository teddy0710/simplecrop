package com.zhangflg.simplecrop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * @author zhangflg
 */
public class CropedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_croped);
        ImageView imageView = (ImageView) findViewById(R.id.iv_show);
        Glide.with(this).load(getIntent().getStringExtra("photo")).into(imageView);
    }
}
