package com.example.concentrationdetect.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;


import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 图片工具类
 */
public class ImageUtil  {
    //得到图片灰度值
    //Gray = (R*299 + G*587 + B*114 + 500) / 1000
    public static float getGrayValue(Bitmap bitmap,int x,int y) {
        /**
         *java读取图像的rpg以及灰度值
         */
        //以文件流的方式读取图片
        float grayValue=0;
        int sum=0;
        if(bitmap!=null) {
            int height = bitmap.getHeight();//图片的高度
            int width = bitmap.getWidth();//图片的宽度
            int pixel;
            for (int i = x-12; i < x+12; i++)//可自行更改起点位置
                for (int j = y-12; j < y+12; j++) {
                    int rgbR, rgbG, rgbB;
                    pixel = bitmap.getPixel(i, j);//下面三行获取像素点（i, j）的RGB值
                    rgbR = (pixel & 0xff0000) >> 16;
                    rgbG = (pixel & 0xff00) >> 8;
                    rgbB = (pixel & 0xff);
                    sum++;
                    grayValue+=(299*rgbR+587*rgbG+114*rgbB+500)/1000;
                    System.out.println("i=" + i + ",j=" + j + ":(" + rgbR + "," + rgbG + "," + rgbB + ")");
                }


        }else{
            Log.e("", "initGrayValue: bitmap为空" );
        }
        grayValue=grayValue/sum;
        return grayValue;
    }
    //处理返回的结果
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getImageAbsolutePath(Intent data, Context context){
        String imagePath=null;
        if(data==null){
            Log.d("", "getImageAbsolutePath: 没有选择图片");
            return null;
        }
        Uri uri=data.getData();
        if(DocumentsContract.isDocumentUri(context,uri)){
            //如果是Document类型文件，则通过Document id处理
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id=docId.split(":")[1];//解析出数字格式的id
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection,context);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null,context);
            }
        }else if("content".equalsIgnoreCase(uri.getScheme())){
            imagePath=getImagePath(uri,null,context);
        }else if("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
        //displayImage(imagePath,context);
    }
    private static String getImagePath(Uri uri,String selection,Context context){
        String path=null;
        //通过Uri和selection获取图片真实路径
        Cursor cursor=context.getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return  path;
    }
}



