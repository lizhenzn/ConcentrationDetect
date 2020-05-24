package com.example.concentrationdetect.controller;

/**
 * 控制类
 */
public interface IController {
    /**
     * 登录
     * @param username
     * @param password
     */
    void login(String username,String password);

    /**
     * 注册
     * @param username
     * @param password
     */
    void regisiter(String username,String password);
}
