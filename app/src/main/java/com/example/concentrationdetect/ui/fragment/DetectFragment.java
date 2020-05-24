package com.example.concentrationdetect.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.concentrationdetect.R;
import com.example.concentrationdetect.constant.IPrivilegeCode;
import com.example.concentrationdetect.ui.activity.TrainModelActivity;
import com.example.concentrationdetect.ui.customView.ActionSheetDialog;
import com.example.concentrationdetect.utils.ImageUtil;
import com.example.concentrationdetect.utils.PrivilegeManager;

import java.io.File;

public class DetectFragment extends Fragment implements View.OnClickListener,View.OnTouchListener {
    private TextView detectResultTV;
    private ImageView detectIV;
    private Bitmap choosedBitmap;
    String absoluteRoad;
    int touchX,touchY;
    public DetectFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        PrivilegeManager.checkNeedPermissions(getContext(),getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        touchY=-1;
        touchX=-1;
        View view=inflater.inflate(R.layout.fragment_detect,container,false);
        detectIV=view.findViewById(R.id.detect_iv);
        detectIV.setOnTouchListener(this);
        detectResultTV=view.findViewById(R.id.detect_result_tv);
        Button detectBtn=view.findViewById(R.id.detect_btn);
        Button choosePhotoBtn=view.findViewById(R.id.choose_photo_btn);
        detectBtn.setOnClickListener(this);
        choosePhotoBtn.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detect_btn:{
                Log.e("", "onClick: 点击检测按钮" );
                if((touchX==-1)||(touchY==-1)){
                    Toast.makeText(getContext(),"没有选定检测区域",Toast.LENGTH_SHORT).show();
                }else{
                    float grayValue=ImageUtil.getGrayValue(choosedBitmap,touchX,touchY);
                    //TODO 得到的结果根据特定函数估计浓度
                    String result="灰度值为"+grayValue;
                    detectResultTV.setText(result);
                }
            }break;
            case R.id.choose_photo_btn:{
                new ActionSheetDialog(getContext())
                        .builder()
                        .setCancelable(false)
                        .setCanceledOnTouchOutside(true)
                        .setTitle("选择图片")
                        .addSheetItem("从相册选择", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                if (PrivilegeManager.checkStorage(getContext())) {
                                    Intent intent = new Intent("android.intent.action.GET_CONTENT");
                                    intent.setType("image/*");
                                    startActivityForResult(intent,IPrivilegeCode.OPEN_ALBUM);
                                } else{
                                    PrivilegeManager.checkNeedPermissions(getContext(),getActivity());
                                }
                            }
                        })
                        .addSheetItem("点击拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Log.d("addTest", "点击拍照——上");
                                if(!PrivilegeManager.checkCamera(getContext()))
                                    PrivilegeManager.checkNeedPermissions(getContext(),getActivity());
                                else {
                                    Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//用来打开相机的Intent
                                    if (takePhotoIntent.resolveActivity(getActivity().getPackageManager()) != null) {//这句作用是如果没有相机则该应用不会闪退，要是不加这句则当系统没有相机应用的时候该应用会闪退
//                                        File picFile = creatImageFile(BASE_DIR, 1);
//                                        upFileName.add(picFile.getName());
//                                        upSeason.add(SEASON_DEFAULT);
//                                        Uri imageUri = FileProvider.getUriForFile(getContext(),
//                                                "com.example.cm",
//                                                picFile);
//                                        takePhotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//                                        startActivityForResult(takePhotoIntent, IPrivilegeCode.OPEN_CAMERA);//启动相机
                                    } else {
                                        Toast.makeText(getContext(),
                                                "没有相机，无法完成操作",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }).show();
            }
            default:break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case IPrivilegeCode.OPEN_ALBUM:{
                absoluteRoad= ImageUtil.getImageAbsolutePath(data,getContext());
                if(absoluteRoad==null){
                    Toast.makeText(getContext(),"获取图片失败",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),"获取图片成功"+absoluteRoad,Toast.LENGTH_SHORT).show();
                    //TODO 传入位置信息
                    //ImageUtil.getGrayValue(absoluteRoad,touchX,touchY);
                     choosedBitmap= BitmapFactory.decodeFile(absoluteRoad);
                    detectIV.setImageBitmap(choosedBitmap);
                }

            }break;
            default:break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case IPrivilegeCode.REQUEST_CAMERA:{
                Log.e("", "onClick: 申请权限结果" );
                if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                    Intent intent=new Intent("android.intent.action.GET_CONTENT");
//                    intent.setType("image/*");
//                    startActivityForResult(intent,IPrivilegeCode.OPEN_ALBUM);
                }else{
                    Toast.makeText(getContext(),"拒绝授予权限",Toast.LENGTH_SHORT).show();
                }

            }break;
            default:break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.detect_iv:{
                Log.e("", "onTouch:点击图片位置 "+"X:"+event.getX()+",Y:"+event.getY() );
                touchX=(int)event.getX();
                touchY=(int)event.getY();
            }break;
            default:break;
        }
        return true;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.train_model_menu:{
                Intent intent=new Intent(getContext(), TrainModelActivity.class);
                startActivity(intent);
            }break;
            default:break;
        }
        return true;
    }
}
