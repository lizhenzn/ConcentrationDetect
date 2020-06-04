package com.example.concentrationdetect.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.concentrationdetect.R;
import com.example.concentrationdetect.constant.IAuthenticateResult;
import com.example.concentrationdetect.constant.ICmdType;
import com.example.concentrationdetect.constant.ILoginResult;
import com.example.concentrationdetect.constant.IRegisiterResult;
import com.example.concentrationdetect.controller.IController;
import com.example.concentrationdetect.controller.impl.ControllerImpl;
import com.example.concentrationdetect.data.GlobalData;
import com.example.concentrationdetect.ui.callback.IAuthenticateCallBack;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

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
              progressDialog.setMessage("登录中...");
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                this.finish();
            }break;
            default:break;
        }
        return true;
    }

    class MyHandle extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            StringBuilder sb= (StringBuilder) msg.obj;
            System.out.println(sb.toString());
            String what=null;
            int result=0;
            String sResult=null;
            try {
                JSONObject jsonObject=new JSONObject(sb.toString());
                what=jsonObject.getString("what");
                result= Integer.parseInt(jsonObject.getString("result"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(what.equalsIgnoreCase(ICmdType.LOGIN)){
                if(result== ILoginResult.SUCCESS){
                    sResult="登陆成功";
                    GlobalData.user.setUserName(userET.getText().toString().trim());
                    GlobalData.isLogined=true;
                    //登陆成功之后获取该用户之前训练的模型
                    IController controller=new ControllerImpl();
                    controller.getModels();

                }else {
                    sResult="登陆失败";
                }
            }else if(what.equalsIgnoreCase(ICmdType.REGISITER)){
                if(result== IRegisiterResult.SUCCESS){
                    sResult="注册成功";
                }else if(result==IRegisiterResult.FAILED){
                    sResult="注册失败";
                }else{
                    sResult="注册账号冲突";
                }
            }
            Toast.makeText(LoginActivity.this,sResult,Toast.LENGTH_LONG).show();
        }
    }
}
