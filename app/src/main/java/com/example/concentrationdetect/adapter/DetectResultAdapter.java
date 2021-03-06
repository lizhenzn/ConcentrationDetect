package com.example.concentrationdetect.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.concentrationdetect.R;


import static com.example.concentrationdetect.ui.activity.TrainModelActivity.groupCount;
import static com.example.concentrationdetect.ui.activity.TrainModelActivity.list;

public class DetectResultAdapter extends BaseAdapter {
    Context context;
    public DetectResultAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.detect_item,parent,false);
        Button grayBtn=view.findViewById(R.id.detect_item_gray_value);
        Button concentrationBtn=view.findViewById(R.id.detect_item_concentration_value);
        grayBtn.setText(String.valueOf(list.get(position).getGrayValue()));
        concentrationBtn.setText(String.valueOf(list.get(position).getConcentrationValue()));
        Button deleteBtn=view.findViewById(R.id.detect_item_delete);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                list.remove(position);
                notifyDataSetChanged();
                groupCount--;
            }
        });
        return view;
    }
}
