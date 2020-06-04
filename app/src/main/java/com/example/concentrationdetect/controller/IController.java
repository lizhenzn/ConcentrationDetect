package com.example.concentrationdetect.controller;

import android.content.Context;

/**
 * 控制类
 */
public interface IController {
    /**
     * 登录
     * @param username
     * @param password
     */
    void login(String username, String password);

    /**
     * 注册
     * @param username
     * @param password
     */
    void regisiter(String username,String password);

    /**
     * 训练模型
     * @param username
     * @param dataStr
     */
    void trainModel(String username,String modelName,String dataStr);
    void getModels();
    void deleteModel(String modelName);
}
