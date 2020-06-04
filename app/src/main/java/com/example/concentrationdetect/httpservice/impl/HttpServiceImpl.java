package com.example.concentrationdetect.httpservice.impl;

import android.content.Context;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.example.concentrationdetect.bean.Model;
import com.example.concentrationdetect.data.GlobalData;
import com.example.concentrationdetect.httpservice.IHttpService;
import com.example.concentrationdetect.ui.activity.LoginActivity;
import com.example.concentrationdetect.ui.activity.ModelDetailActivity;
import com.example.concentrationdetect.ui.callback.IAuthenticateCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                       message.obj=sb;
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
    public void trainModel(final String cmd, final String dataStr) {
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
                        JSONObject jsonObject=new JSONObject(sb.toString());
                        String s=jsonObject.getString("result");
                        String modelName=jsonObject.getString("modelName");
                        float p1= (float) jsonObject.getDouble("parm1");
                        float p2= (float) jsonObject.getDouble("parm2");
                        float[] parms=new float[2];
                        parms[0]=p1;
                        parms[1]=p2;
                        Model model=new Model();
                        model.setName(modelName);
                        model.setArguments(parms);
                        GlobalData.models.add(model);

                    }
                    conn.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    @Override
    public void deleteModel(final String cmd) {
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
                        message.obj=sb;
                        message.setTarget(ModelDetailActivity.handle);
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
    public void getModels(final String cmd) {
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

                        JSONObject jsonObject0=new JSONObject(sb.toString());
                        String s0=jsonObject0.getString("result");
                        if(s0.charAt(0)=='{') {
                            String[] s = s0.split(";");
                            GlobalData.models.clear();//清理列表
                            for (int i = 0; i < s.length; i++) {
                                JSONObject jsonObject = new JSONObject(s[i]);
                                Model model = new Model();
                                model.setName(jsonObject.getString("modelName"));
                                float p1 = (float) jsonObject.getDouble("parm1");
                                float p2 = (float) jsonObject.getDouble("parm2");
                                float[] a = new float[2];
                                a[0] = p1;
                                a[1] = p2;
                                model.setArguments(a);
                                GlobalData.models.add(model);
                            }
                        }
                    }
                    urlConnection.disconnect();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}
