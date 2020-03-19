package com.example.myfriends;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;

public class BitmapUtil {
    public static byte[] toBytes(Bitmap bm){
        if(bm == null)
            return null;
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 0, byteStream);
        byte bitmapBytes[] = byteStream.toByteArray();
        return bitmapBytes;
    }

    public static Bitmap fromBytes(byte[] bytes){
        if(bytes == null)
            return null;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
}
