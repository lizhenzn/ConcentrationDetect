package com.example.concentrationdetect.httpservice.impl;

import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.example.concentrationdetect.httpservice.IHttpService;
import com.example.concentrationdetect.ui.activity.LoginActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Http请求接口实现类
 */
public class HttpServiceImpl implements IHttpService {

    @Override
    public void sendCmd(final String cmd) {
       new Thread(new Runnable() {
           @Override
           public void run() {
               String url_str=urlhead+cmd;
               try {
                   URL url = new URL(url_str);
                   HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                   Log.e("发送的URL", "run: "+url_str );
                   if(urlConnection.getResponseCode()==200) {
                       InputStream inputStream = urlConnection.getInputStream();
                       byte[] buff = new byte[1024];
                       int len = -1;
                       StringBuilder sb = new StringBuilder();
                       while ((len = inputStream.read(buff)) != -1) {
                           sb.append(new String(buff,0,len,"utf-8"));
                       }
                       Log.e("接收到", "run: "+sb.toString() );
                       Message message=Message.obtain();
                       message.setTarget(LoginActivity.handle);
                       message.sendToTarget();
                   }
                   urlConnection.disconnect();
               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (UnsupportedEncodingException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }
       }).start();
    }

    @Override
    public void trainModel(final String cmd,final String dataStr) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url_str=urlhead+cmd;
                try {
                    URL url = new URL(url_str);
                    HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                    Log.e("发送的URL", "run: "+url_str );
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    conn.setUseCaches(false);
                    conn.setRequestProperty("Connection", "Keep-Alive");
                    conn.setRequestProperty("Charset", "UTF-8");
                    // 设置文件类型:
                    conn.setRequestProperty("Content-Type","application/json; charset=UTF-8");
                    // 设置接收类型否则返回415错误
                    //conn.setRequestProperty("accept","*/*")此处为暴力方法设置接受所有类型，以此来防范返回415;
                    conn.setRequestProperty("accept","application/json");
                    // 往服务器里面发送数据
                    if (dataStr != null && !TextUtils.isEmpty(dataStr)) {
                        byte[] writebytes = dataStr.getBytes();
                        // 设置文件长度
                        conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
                        OutputStream outwritestream = conn.getOutputStream();
                        outwritestream.write(dataStr.getBytes());
                        outwritestream.flush();
                        outwritestream.close();
                        Log.d("hlhupload", "doJsonPost: conn"+conn.getResponseCode());
                    }

                    if(conn.getResponseCode()==200) {
                        InputStream inputStream = conn.getInputStream();
                        byte[] buff = new byte[1024];
                        int len = -1;
                        StringBuilder sb = new StringBuilder();
                        while ((len = inputStream.read(buff)) != -1) {
                            sb.append(new String(buff,0,len,"utf-8"));
                        }
                        Log.e("接收到", "run: "+sb.toString() );
                        Message message=Message.obtain();
                        message.setTarget(LoginActivity.handle);
                        message.sendToTarget();
                    }
                    conn.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }
}
