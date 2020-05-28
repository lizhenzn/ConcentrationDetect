package com.example.concentrationdetect.controller;

import android.content.Context;

/**
 * 控制类
 */
public interface IController {
    /**
     * 登录
     * @param context
     * @param username
     * @param password
     */
    void login(Context context,String username, String password);

    /**
     * 注册
     * @param context
     * @param username
     * @param password
     */
    void regisiter(Context context,String username,String password);

    /**
     * 训练模型
     * @param username
     * @param dataStr
     */
    void trainModel(Context context,String username,String dataStr);
}
