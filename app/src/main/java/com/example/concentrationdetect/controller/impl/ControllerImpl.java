package com.example.concentrationdetect.controller.impl;

import android.content.Context;
import android.os.Handler;

import com.example.concentrationdetect.controller.IController;
import com.example.concentrationdetect.data.GlobalData;
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

    @Override
    public void trainModel(String username, String modelName,String dataStr) {
        String cmd="?what=train&username="+username+"&modelName="+modelName+"&groupCount="+ TrainModelActivity.groupCount;
        httpService=new HttpServiceImpl();
        httpService.trainModel(cmd,dataStr);
    }

    @Override
    public void getModels() {
        String cmd="?what=getModels&username="+ GlobalData.user.getUserName();
        httpService=new HttpServiceImpl();
        httpService.getModels(cmd);
    }

    @Override
    public void deleteModel(String modelName) {
        String cmd="?what=deleteModel&username="+ GlobalData.user.getUserName() +"&modelName="+modelName;
        httpService=new HttpServiceImpl();
        httpService.deleteModel(cmd);
    }
}
