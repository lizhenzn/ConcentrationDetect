package com.example.concentrationdetect.ui.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;

import com.example.concentrationdetect.R;
import com.example.concentrationdetect.adapter.MainFragmentPagerAdapter;
import com.example.concentrationdetect.bean.User;
import com.example.concentrationdetect.data.GlobalData;
import com.example.concentrationdetect.ui.fragment.DetectFragment;
import com.example.concentrationdetect.ui.fragment.MineFragment;
import com.example.concentrationdetect.utils.PrivilegeManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private ViewPager viewPager;
private RadioButton detectRB,mineRB;
private List<Fragment> fragmentList;
private FragmentManager fragmentManager;
private MainFragmentPagerAdapter pagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);//不显示标题
        setContentView(R.layout.activity_main);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            //actionBar.setDisplayShowTitleEnabled(false);
        }
        setContentView(R.layout.activity_main);
        initWidget();
        initData();
    }

    /**
     * 初始化控件
     */
    private void initWidget(){
        viewPager=findViewById(R.id.main_vp);
        detectRB=findViewById(R.id.detect_rb);
        detectRB.setOnClickListener(this);
        mineRB=findViewById(R.id.mine_rb);
        mineRB.setOnClickListener(this);
        chooseDetect();
    }

    /**
     * 初始化数据
     */
    private void initData(){
        GlobalData.isLogined=false;
        GlobalData.user=new User();
        GlobalData.models=new ArrayList<>();
        GlobalData.currentModelIndex=-1;
        fragmentList=new ArrayList<>();
        fragmentManager=getSupportFragmentManager();
        fragmentList.add(new DetectFragment());
        fragmentList.add(new MineFragment());
        pagerAdapter=new MainFragmentPagerAdapter(fragmentManager,fragmentList);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.detect_rb:{
                viewPager.setCurrentItem(0);
                chooseDetect();
            }break;
            case R.id.mine_rb:{
                viewPager.setCurrentItem(1);
                chooseMine();
            }break;
            default:break;
        }
    }
    /**
     * 进入检测页面
     */
    private void chooseDetect(){
        setTitle("检测");
        Drawable detectDraw = getResources().getDrawable(R.drawable.detect);
        detectDraw.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        detectRB.setCompoundDrawables(null, detectDraw, null, null);//只放上面
        Drawable mineDraw = getResources().getDrawable(R.drawable.ic_launcher_background);
        mineDraw.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        mineRB.setCompoundDrawables(null, mineDraw, null, null);//只放上面
    }
    /**
     * 进入我的页面
     */
    private void chooseMine(){
        setTitle("我的");
        Drawable detectDraw = getResources().getDrawable(R.drawable.ic_launcher_background);
        detectDraw.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        detectRB.setCompoundDrawables(null, detectDraw, null, null);//只放上面
        Drawable mineDraw = getResources().getDrawable(R.drawable.unlogin);
        mineDraw.setBounds(0, 0, 69, 69);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        mineRB.setCompoundDrawables(null, mineDraw, null, null);//只放上面
    }



}
