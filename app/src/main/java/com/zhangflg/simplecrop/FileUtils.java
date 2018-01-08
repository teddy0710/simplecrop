package com.zhangflg.simplecrop;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by ZFL on 2018/1/8
 */

public class FileUtils {
    public static File getFileFromUri(Uri uri, Context context) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor actualimagecursor = ((Activity) context).managedQuery(uri, proj, null, null, null);
        int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor.getString(actual_image_column_index);
        File file = new File(img_path);
        return file;
    }

    public static String getFilePathFromUri(Uri uri, Context context) {
        return getFileFromUri(uri, context).getAbsolutePath();
    }
}
