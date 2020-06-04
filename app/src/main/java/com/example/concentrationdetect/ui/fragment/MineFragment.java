package com.example.concentrationdetect.ui.fragment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.concentrationdetect.R;
import com.example.concentrationdetect.data.GlobalData;
import com.example.concentrationdetect.ui.activity.LoginActivity;
import com.example.concentrationdetect.ui.activity.ModelDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
private ImageView mineIV;
private TextView mineTV;
private Bitmap bitmap;
private LinearLayout linearLayout;
private Button singOutBtn;

    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_mine,container,false);
        mineIV=view.findViewById(R.id.mine_user_iv);
        mineTV=view.findViewById(R.id.mine_user_tv);
        mineIV.setOnClickListener(this);
        mineTV.setOnClickListener(this);
        linearLayout=view.findViewById(R.id.mine_model_ll);
        linearLayout.setOnClickListener(this);
        singOutBtn=view.findViewById(R.id.mine_signout_btn);
        singOutBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_user_iv:{

            }break;
            case R.id.mine_user_tv:{
                String user=mineTV.getText().toString();
                if(user.equalsIgnoreCase("未登录")){
                    Intent intent=new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }break;
            case R.id.mine_signout_btn:{
                GlobalData.isLogined=false;
                bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.unlogin);
                mineIV.setImageBitmap(bitmap);
                mineTV.setText("未登录");
            }break;
            case R.id.mine_model_ll:{
                if(GlobalData.isLogined){
                    Intent intent=new Intent(getActivity(), ModelDetailActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(),"请先登录",Toast.LENGTH_SHORT).show();
                }
            }break;
            default:break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(GlobalData.isLogined){
            bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.defaulthb);
            mineTV.setText(GlobalData.user.getUserName());
        }else{
            bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.unlogin);
        }
        mineIV.setImageBitmap(bitmap);
    }
}
