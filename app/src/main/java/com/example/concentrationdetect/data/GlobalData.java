package com.example.concentrationdetect.data;

import com.example.concentrationdetect.bean.Model;
import com.example.concentrationdetect.bean.User;

import java.util.ArrayList;

/**
 * 全局数据类,多个页面需要用到的信息
 */
public class GlobalData {
    public static User user;//用户信息
    public static boolean isLogined;//是否登录
    public static ArrayList<Model> models;//本地模型数据数组
    public static int currentModelIndex;//当前采用的模型id

}
