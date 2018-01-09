package com.zhangflg.simplecrop;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.isseiaoki.simplecropview.CropImageView;
import com.isseiaoki.simplecropview.callback.CropCallback;
import com.isseiaoki.simplecropview.callback.LoadCallback;
import com.isseiaoki.simplecropview.callback.SaveCallback;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;

import java.io.File;
import java.util.List;


/**
 * @author zhangflg
 */
public class CropActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int REQUEST_CODE_CHOOSE = 998;
    public static final int REQUEST_CODE_CROP = 997;
    private List<Uri> mSelected;
    private CropImageView mCropView;
    private Uri sourceUri;
    private Uri newUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop);
        mCropView = (CropImageView) findViewById(R.id.cropImageView);
        initChoosePhoto();
    }

    private void initChoosePhoto() {
        findViewById(R.id.iv_choose_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSelected != null) {
                    mSelected.clear();
                }
                Matisse.from(CropActivity.this)
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
    }

    private void initView() {
        mCropView.load(sourceUri).useThumbnail(true).execute(new LoadCallback() {
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
        mCropView.setCompressFormat(Bitmap.CompressFormat.JPEG);
        mCropView.setCompressQuality(100);
        mCropView.setAnimationEnabled(true);
        mCropView.setAnimationDuration(500);
        mCropView.setInterpolator(new AccelerateDecelerateInterpolator());
        //        mCropView.setBackgroundColor
//        mCropView.setOverlayColor
//        mCropView.setFrameColor 框
//        mCropView.setHandleColor 点
//        mCropView.setGuideColor 线

//        mCropView.setFrameStrokeWeightInDp(1);
//        mCropView.setGuideStrokeWeightInDp(1);
//        mCropView.setHandleSizeInDp();

//        mCropView.setTouchPaddingInDp(16);
//        mCropView.setHandleShowMode(CropImageView.ShowMode.SHOW_ALWAYS);
//        mCropView.setGuideShowMode(CropImageView.ShowMode.SHOW_ON_TOUCH);
//        mCropView.setDebug(true);
//        mCropView.setOutputMaxSize(300, 300);
//        setOutputWidth(100); // If cropped image size is 400x200, output size is 100x50
//        setOutputHeight(100); // If cropped image size is 400x200, output size is 200x100
//        mCropView.setMinFrameSizeInDp(100);//设置最小裁剪范围

        mCropView.setInitialFrameScale(0.75f);//初始选框大小
        TextView tv916 = (TextView) findViewById(R.id.tv_916);
        tv916.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_9_16);
            }
        });
        TextView tv169 = (TextView) findViewById(R.id.tv_169);
        tv169.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_16_9);
            }
        });
        TextView tv34 = (TextView) findViewById(R.id.tv_34);
        tv34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_3_4);
            }
        });
        TextView tv43 = (TextView) findViewById(R.id.tv_43);
        tv43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.setCropMode(CropImageView.CropMode.RATIO_4_3);
            }
        });
        TextView tvS = (TextView) findViewById(R.id.tv_shun);
        tvS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_90D);

            }
        });
        TextView tvN = (TextView) findViewById(R.id.tv_ni);
        tvN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.rotateImage(CropImageView.RotateDegrees.ROTATE_M90D);
            }
        });
        findViewById(R.id.tv_free_rect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.setCropMode(CropImageView.CropMode.FREE);
            }
        });
        findViewById(R.id.tv_default).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.setCropMode(CropImageView.CropMode.SQUARE);
            }
        });
        findViewById(R.id.tv_fit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.setCropMode(CropImageView.CropMode.FIT_IMAGE);
            }
        });
        findViewById(R.id.tv_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.setCropMode(CropImageView.CropMode.CIRCLE);
            }
        });
        findViewById(R.id.tv_rect_circle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.setCropMode(CropImageView.CropMode.CIRCLE_SQUARE);
            }
        });
        findViewById(R.id.tv_storage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCropView.getActualCropRect();
            }
        });

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
                                        Log.e("CropCallback", "裁剪图片成功" + uri.toString());
                                        Glide.with(CropActivity.this).load(newUri).into(mCropView);
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.e("CropCallback", "裁剪图片失败:" + e.toString());
                                    }
                                });
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            Glide.with(this).load(mSelected.get(0)).into(mCropView);
            sourceUri = Uri.parse(String.valueOf(mSelected.get(0)));
            String sourceFilePath = FileUtils.getFilePathFromUri(sourceUri, this);
            String newFilePath = sourceFilePath.substring(0, sourceFilePath.length() - 4) + "c.jpg";
            File file = new File(newFilePath);
            newUri = Uri.fromFile(file);
            initView();

        }
    }
}
