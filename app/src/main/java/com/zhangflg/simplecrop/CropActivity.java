package com.zhangflg.simplecrop;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

/**
 * @author zhangflg
 */
public class CropActivity extends AppCompatActivity implements View.OnClickListener {

    private CropImageView mCropView;
    private Uri sourceUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        sourceUri = Uri.parse(getIntent().getStringExtra("sourceUri"));
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        mCropView.load(sourceUri).execute(new LoadCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Throwable e) {

            }
        });
        findViewById(R.id.iv_crop).setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_crop:
                doCrop();
                break;
            default:
                break;
        }
    }

    private void doCrop() {
        mCropView.crop(sourceUri)
                .execute(new CropCallback() {
                    @Override
                    public void onSuccess(Bitmap cropped) {
                        mCropView.save(cropped)
                                .execute(sourceUri, new SaveCallback() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
}
