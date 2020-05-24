package com.example.concentrationdetect.httpservice.impl;

import android.os.Message;
import android.util.Log;

import com.example.concentrationdetect.httpservice.IHttpService;
import com.example.concentrationdetect.ui.activity.LoginActivity;

import java.io.IOException;
import java.io.InputStream;
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
}
