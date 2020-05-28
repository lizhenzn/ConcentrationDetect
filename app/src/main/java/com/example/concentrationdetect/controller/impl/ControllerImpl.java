package com.example.concentrationdetect.controller.impl;

import android.content.Context;
import android.os.Handler;

import com.example.concentrationdetect.controller.IController;
import com.example.concentrationdetect.httpservice.IHttpService;
import com.example.concentrationdetect.httpservice.impl.HttpServiceImpl;
import com.example.concentrationdetect.ui.activity.TrainModelActivity;

/**
 * 控制接口实现类
 */
public class ControllerImpl implements IController {
private IHttpService httpService;
public static Handler handler;
    @Override
    public void login(Context context,String username, String password) {
        String cmd="?what=login&username="+username+"&password="+password;
        httpService=new HttpServiceImpl();
        httpService.sendCmd(context,cmd);
    }

    @Override
    public void regisiter(Context context,String username, String password) {
        String cmd="?what=regisiter&username="+username+"&password="+password;
        httpService=new HttpServiceImpl();
        httpService.sendCmd(context,cmd);
    }

    @Override
    public void trainModel(Context context,String username, String dataStr) {
        String cmd="?what=train&username="+username+"&groupCount="+ TrainModelActivity.groupCount;
        httpService=new HttpServiceImpl();
        httpService.trainModel(context,cmd,dataStr);
    }
}
