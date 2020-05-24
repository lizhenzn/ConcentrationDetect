package com.example.concentrationdetect.controller.impl;

import android.os.Handler;

import com.example.concentrationdetect.controller.IController;
import com.example.concentrationdetect.httpservice.IHttpService;
import com.example.concentrationdetect.httpservice.impl.HttpServiceImpl;

/**
 * 控制接口实现类
 */
public class ControllerImpl implements IController {
private IHttpService httpService;
public static Handler handler;
    @Override
    public void login(String username, String password) {
        String cmd="?what=login&username="+username+"&password="+password;
        httpService=new HttpServiceImpl();
        httpService.sendCmd(cmd);
    }

    @Override
    public void regisiter(String username, String password) {
        String cmd="?what=regisiter&username="+username+"&password="+password;
        httpService=new HttpServiceImpl();
        httpService.sendCmd(cmd);
    }
}
