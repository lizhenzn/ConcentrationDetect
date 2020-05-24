package com.example.concentrationdetect.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.concentrationdetect.R;
import com.example.concentrationdetect.controller.IController;
import com.example.concentrationdetect.controller.impl.ControllerImpl;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
private Button loginBtn,regisiterBtn;
private EditText userET,passwordET;
private ProgressDialog progressDialog;
public static MyHandle handle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//不显示标题
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);//返回键
        }
        setContentView(R.layout.activity_login);
        init();
    }

    /**
     * 初始化控件和数据
     */
    private void init(){
        handle=new MyHandle();
        loginBtn=findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);
        regisiterBtn=findViewById(R.id.regisiter_btn);
        regisiterBtn.setOnClickListener(this);
        userET=findViewById(R.id.username);
        passwordET=findViewById(R.id.password);
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("");
        //progressDialog.setMessage("登陆中...");
        progressDialog.setCancelable(false);
        //progressDialog.show();

    }

    @Override
    public void onClick(View v) {

//        if((user==null)||(user.length()<=0)){
//            Toast.makeText(this,R.string.empty_username,Toast.LENGTH_SHORT).show();
//            return;
//        }
//        if((password==null)||(password.length()<=0)){
//            Toast.makeText(this,R.string.empty_password,Toast.LENGTH_SHORT).show();
//            return;
//        }
        switch (v.getId()){
            case R.id.login_btn:{
                Log.e("", "onClick: 点击了login" );
                String user=userET.getText().toString().trim();
                String password=passwordET.getText().toString().trim();
                IController controller=new ControllerImpl();
                controller.login(user,password);
              progressDialog.setMessage("登陆中...");
              progressDialog.show();
            }break;
            case R.id.regisiter_btn:{
                String user=userET.getText().toString().trim();
                String password=passwordET.getText().toString().trim();
                IController controller=new ControllerImpl();
                controller.regisiter(user,password);
                progressDialog.setMessage("注册中...");
                progressDialog.show();
            }break;
            default:break;
        }
    }
    class MyHandle extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            Toast.makeText(LoginActivity.this,"SUCCESS!",Toast.LENGTH_LONG).show();
        }
    }
}
