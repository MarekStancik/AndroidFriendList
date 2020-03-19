package com.example.myfriends;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class SerializableBitmap implements Serializable {

    public Bitmap bitmap;

    // TODO: Finish this constructor
    public SerializableBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    // Converts the Bitmap into a byte array for serialization
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        byte [] bitmapBytes = BitmapUtil.toBytes(bitmap);
        out.write(bitmapBytes, 0, bitmapBytes.length);
    }

    // Deserializes a byte array representing the Bitmap and decodes it
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        int b;
        while((b = in.read()) != -1)
            byteStream.write(b);
        byte bitmapBytes[] = byteStream.toByteArray();
        bitmap = BitmapUtil.fromBytes(bitmapBytes);
    }

    public Bitmap getBitmap(){
        return bitmap;
    }
}