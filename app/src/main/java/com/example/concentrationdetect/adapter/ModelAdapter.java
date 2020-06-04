package com.example.concentrationdetect.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.concentrationdetect.R;
import com.example.concentrationdetect.controller.IController;
import com.example.concentrationdetect.controller.impl.ControllerImpl;
import com.example.concentrationdetect.data.GlobalData;
import com.example.concentrationdetect.ui.activity.ModelDetailActivity;


public class ModelAdapter extends BaseAdapter {
    private Context context;
    public ModelAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return GlobalData.models.size();
    }

    @Override
    public Object getItem(int position) {
        return GlobalData.models.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.model_item,parent,false);
        TextView name_tv=view.findViewById(R.id.model_item_name);
        TextView model_tv=view.findViewById(R.id.model_item_model);
        TextView delete_tv=view.findViewById(R.id.model_item_delete);
        final String name=GlobalData.models.get(position).getName();
        float[] argvs=GlobalData.models.get(position).getArguments();
        String model="y="+argvs[0]+"*x+"+argvs[1];
        name_tv.setText(name);
        model_tv.setText(model);
        delete_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("提示")
                        .setMessage("您确定删除此模型:"+name)
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("", "onClick: 点击了确认" );
                                ((ModelDetailActivity)context).deleteID=position;
                                IController controller=new ControllerImpl();
                                controller.deleteModel(name);
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle("提示")
                        .setMessage("您确定设置此模型为默认使用模型:")
                        .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.e("", "onClick: 点击了确认" );
                                GlobalData.currentModelIndex=position;
                                Toast.makeText(context,"设置默认模型成功",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();
                return false;
            }
        });
        return view;
    }
}
