package com.example.concentrationdetect.httpservice;

import android.content.Context;

/**
 * Http请求接口类
 */
public interface IHttpService {
    final String urlhead="http://192.168.0.104:8080//WebServer_war_exploded/DealCmd";
    /**
     * 向服务器发送命令
     * @param cmd
     */
    void sendCmd(String cmd);

    /**
     * 发送训练模型数据
     * @param cmd
     * @param dataStr
     */
    void trainModel(String cmd,String dataStr);
    void deleteModel(String cmd);
    void getModels(String cmd);
}
