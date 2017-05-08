package com.example.kosta.cameraexam;

import android.app.Activity;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;

/**
 * Created by kosta on 2017-05-01.
 */

public class PictureUtils {
    public static Bitmap getScaleBitmap(String path, int destWidth, int destHeight) {
//        파일 이미지의 크기를 판별
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;

//        이미지 크기 조정 파악
        int inSampleSize = 1;
        if(srcHeight > destHeight || srcWidth > destWidth) {
            if(srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight/srcHeight);
            } else {
                inSampleSize = Math.round(srcWidth/destWidth);
            }
        }

        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        Matrix rotate = new Matrix();
        rotate.postRotate(90);

        Bitmap result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), rotate, false);

        return result;
    }

    public static Bitmap getScaleBitmap(String path, Activity activity) {
        Point size = new Point();
        activity.getWindowManager().getDefaultDisplay().getSize(size);
        return getScaleBitmap(path, size.x, size.y);
    }
}
