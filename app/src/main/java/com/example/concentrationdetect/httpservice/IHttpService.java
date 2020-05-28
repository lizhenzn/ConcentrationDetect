package com.example.concentrationdetect.httpservice;

import android.content.Context;

/**
 * Http请求接口类
 */
public interface IHttpService {
    final String urlhead="http://192.168.56.1:8080//WebServer_war_exploded/DealCmd";
    /**
     * 向服务器发送命令
     * @param context
     * @param cmd
     */
    void sendCmd(Context context,String cmd);

    /**
     * 发送训练模型数据
     * @param cmd
     * @param dataStr
     */
    void trainModel(Context context,String cmd,String dataStr);
}
