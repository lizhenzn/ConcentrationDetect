package com.example.concentrationdetect.ui.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.concentrationdetect.R;
import com.example.concentrationdetect.adapter.DetectResultAdapter;
import com.example.concentrationdetect.bean.DetectResultItem;
import com.example.concentrationdetect.constant.IPrivilegeCode;
import com.example.concentrationdetect.controller.IController;
import com.example.concentrationdetect.controller.impl.ControllerImpl;
import com.example.concentrationdetect.ui.customView.ActionSheetDialog;
import com.example.concentrationdetect.utils.ImageUtil;
import com.example.concentrationdetect.utils.PrivilegeManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TrainModelActivity extends AppCompatActivity implements View.OnTouchListener, View.OnClickListener {
private DetectResultAdapter detectResultAdapter;
private List<DetectResultItem> list;
private ImageView imageView;
private Button addBtn,trainBtn;
private Button choosePhotoBtn;
private ListView listView;
private EditText grayET,concentrationET;
private String absoluteRoad;
private Bitmap choosedBitmap;
private float grayValue,concentrationValue;
public static int groupCount=0;//数据组数
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train_model);
        init();
    }
    private void init(){
        groupCount=0;
        list=new ArrayList<>();
        listView=findViewById(R.id.train_lv);
        addBtn=findViewById(R.id.train_add_btn);
        addBtn.setOnClickListener(this);
        trainBtn=findViewById(R.id.train_btn);
        trainBtn.setOnClickListener(this);
        choosePhotoBtn=findViewById(R.id.train_choose_photo_btn);
        choosePhotoBtn.setOnClickListener(this);
        grayET=findViewById(R.id.train_gray_value_et);
        concentrationET=findViewById(R.id.train_concentration_value_et);
        imageView=findViewById(R.id.train_photo_iv);
        imageView.setOnTouchListener(this);
        detectResultAdapter=new DetectResultAdapter(this,list);
        listView.setAdapter(detectResultAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.train_btn:{
                // xi=np.array([46.47,44.11,42.57,40.87,35.35,28.93,22.20,18.62,12.30]);
                // yi=np.array([0.1,0.5,1,2,5,8,10,12,15]);
                StringBuilder sb=new StringBuilder();
                for(int i=0;i<list.size();i++){
                    if(i!=0){
                        sb.append(",");
                    }
                    sb.append(list.get(i).getGrayValue());
                }
                for(int i=0;i<list.size();i++){
                    sb.append(",");
                    sb.append(list.get(i).getConcentrationValue());
                }
                IController controller=new ControllerImpl();
                controller.trainModel(this,"test",sb.toString());
            }break;
            case R.id.train_add_btn:{
                grayValue=Float.valueOf(String.valueOf(grayET.getText()));
                concentrationValue=Float.valueOf(String.valueOf(concentrationET.getText()));
                DetectResultItem item=new DetectResultItem();
                item.setGrayValue(grayValue);
                item.setConcentrationValue(concentrationValue);
                list.add(item);
                groupCount++;
                detectResultAdapter.notifyDataSetChanged();
            }break;
            case R.id.train_choose_photo_btn:{
                new ActionSheetDialog(this)
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(true)
                        .setTitle("选择图片")
                        .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (PrivilegeManager.checkStorage(TrainModelActivity.this)) {
                                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                                    intent.setType("image/*");
                                    startActivityForResult(intent,IPrivilegeCode.OPEN_ALBUM);
                                } else{
                                    PrivilegeManager.checkNeedPermissions(TrainModelActivity.this,getParent());
                                }
                            }
                        })
                        .addSheetItem("点击拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Log.d("addTest", "点击拍照——上");
                                if(!PrivilegeManager.checkCamera(TrainModelActivity.this))
                                    PrivilegeManager.checkNeedPermissions(TrainModelActivity.this,getParent());
                                else {
                                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
                                    if (takePhotoIntent.resolveActivity(TrainModelActivity.this.getPackageManager()) != null) {//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
//                                        File picFile = creatImageFile(BASE_DIR, 1);
//                                        upFileName.add(picFile.getName());
//                                        upSeason.add(SEASON_DEFAULT);
//                                        Uri imageUri = FileProvider.getUriForFile(getContext(),
//                                                "com.example.cm",
//                                                picFile);
//                                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                                        startActivityForResult(takePhotoIntent, IPrivilegeCode.OPEN_CAMERA);//启动相机
                                    } else {
                                        Toast.makeText(TrainModelActivity.this,
                                                "没有相机，无法完成操作",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }).show();
            }break;
            default:break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.train_photo_iv:{
                if(choosedBitmap!=null) {
                    float grayValue1 = ImageUtil.getGrayValue(choosedBitmap, (int) event.getX(), (int) event.getY());
                    grayET.setText(String.valueOf(grayValue1));
                }else{
                    Toast.makeText(this,"没有选择图片",Toast.LENGTH_SHORT).show();
                }
            }break;
            default:break;
        }
        return true;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case IPrivilegeCode.OPEN_ALBUM:{
                absoluteRoad= ImageUtil.getImageAbsolutePath(data,TrainModelActivity.this);
                if(absoluteRoad==null){
                    Toast.makeText(TrainModelActivity.this,"获取图片失败",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(TrainModelActivity.this,"获取图片成功"+absoluteRoad,Toast.LENGTH_SHORT).show();
                    //TODO 传入位置信息
                    //ImageUtil.getGrayValue(absoluteRoad,touchX,touchY);
                    choosedBitmap= BitmapFactory.decodeFile(absoluteRoad);
                    imageView.setImageBitmap(choosedBitmap);
                }

            }break;
            default:break;
        }
    }
}
