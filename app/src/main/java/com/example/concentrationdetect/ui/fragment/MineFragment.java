package com.example.concentrationdetect.ui.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.concentrationdetect.R;
import com.example.concentrationdetect.ui.activity.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment implements View.OnClickListener {
private ImageView mineIV;
private TextView mineTV;

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
            default:break;
        }
    }
}
