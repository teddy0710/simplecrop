package com.zhangflg.simplecrop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.util.List;

/**
 * @author zhangflg
 */
public class MainActivity extends AppCompatActivity {
    private List<Uri> mSelected;
    public static final int REQUEST_CODE_CHOOSE = 998;
    public static final int REQUEST_CODE_CROP = 997;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.iv_show_photo);
        findViewById(R.id.iv_choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Matisse.from(MainActivity.this)
                        .choose(MimeType.allOf())
                        .countable(true)
                        .maxSelectable(1)
                        .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                        .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                        .thumbnailScale(0.85f)
                        .imageEngine(new GlideEngine())
                        .forResult(REQUEST_CODE_CHOOSE);
            }
        });
        findViewById(R.id.iv_crop_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelected == null || mSelected.size() < 1) {
                    return;
                }
                String path = FileUtils.getFilePathFromUri(mSelected.get(0), MainActivity.this);
                Log.d("Matisse", "mSelected: " + mSelected);
                Log.d("Matisse", "path: " + path);
                Intent intent = new Intent();
                intent.putExtra("sourceUri", mSelected.get(0).toString());
                intent.setClass(MainActivity.this, CropActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CROP);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Glide.with(this).load(mSelected.get(0)).into(imageView);
        }
        if (requestCode == REQUEST_CODE_CROP && resultCode == RESULT_OK) {
            String path = data.getStringExtra("result");
            Glide.with(this).load(path).into(imageView);
        }

    }
}
