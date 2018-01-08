package com.zhangflg.simplecrop;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.LoadCallback;

/**
 * @author zhangflg
 */
public class CropActivity extends AppCompatActivity implements LoadCallback {

    private CropImageView mCropView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        Uri sourceUri = Uri.parse(getIntent().getStringExtra("sourceUri"));
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        mCropView.load(sourceUri).execute(this);
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
