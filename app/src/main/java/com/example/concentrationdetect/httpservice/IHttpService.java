package com.example.concentrationdetect.httpservice;

/**
 * Http请求接口类
 */
public interface IHttpService {
    final String urlhead="http://192.168.56.1:8080//WebServer_war_exploded/DealCmd";
    /**
     * 向服务器发送命令
     * @param cmd
     */
    void sendCmd(String cmd);
}
