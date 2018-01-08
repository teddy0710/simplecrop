package com.zhangflg.simplecrop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;

import java.io.File;


/**
 * @author zhangflg
 */
public class CropActivity extends AppCompatActivity implements View.OnClickListener {

    private CropImageView mCropView;
    private Uri sourceUri;
    private Uri newUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        sourceUri = Uri.parse(getIntent().getStringExtra("sourceUri"));
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        mCropView.load(sourceUri).execute(new LoadCallback() {
            @Override
            public void onSuccess() {
                Log.e("LoadCallback", "加载图片成功");
            }

            @Override
            public void onError(Throwable e) {
                Log.e("LoadCallback", "加载图片失败:" + e.toString());
            }
        });
        findViewById(R.id.iv_crop).setOnClickListener(this);
        String sourceFilePath = FileUtils.getFilePathFromUri(sourceUri, this);
        String newFilePath = sourceFilePath.substring(0, sourceFilePath.length() - 4) + "c.jpg";
        File file = new File(newFilePath);
        newUri = Uri.fromFile(file);
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
                                .execute(newUri, new SaveCallback() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Intent intent = new Intent();
                                        intent.putExtra("result", uri.toString());
                                        setResult(RESULT_OK, intent);
                                        finish();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("CropCallback", "加载图片失败:" + e.toString());
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }
}
