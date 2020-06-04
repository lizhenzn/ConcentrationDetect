package com.example.concentrationdetect.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ListView;
import android.widget.Toast;

import com.example.concentrationdetect.R;
import com.example.concentrationdetect.adapter.ModelAdapter;
import com.example.concentrationdetect.data.GlobalData;

import org.json.JSONException;
import org.json.JSONObject;

public class ModelDetailActivity extends AppCompatActivity {
public static MyHandle handle;
public static int deleteID;
public static ProgressDialog progressDialog;
private ListView listView;
private ModelAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//不显示标题
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);//返回键
        }
        handle=new MyHandle();
        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("");
        //progressDialog.setMessage("登陆中...");
        progressDialog.setCancelable(false);

        setContentView(R.layout.activity_model_detail);
        adapter=new ModelAdapter(this);
        listView=findViewById(R.id.model_lv);
        listView.setAdapter(adapter);
    }
    class MyHandle extends Handler{
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            StringBuilder sb= (StringBuilder) msg.obj;
            try {
                JSONObject jsonObject=new JSONObject(sb.toString());
                int result=jsonObject.getInt("result");
                if(result==1){
                    GlobalData.models.remove(deleteID);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(ModelDetailActivity.this,"删除成功",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ModelDetailActivity.this,"删除失败",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
        return super.onOptionsItemSelected(item);
    }
}
