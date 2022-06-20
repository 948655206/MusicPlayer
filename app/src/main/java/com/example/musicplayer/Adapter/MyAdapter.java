package com.example.musicplayer.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.musicplayer.Bean.Bean;
import com.example.musicplayer.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<Bean> data;
    private Context  context;

    public MyAdapter(List<Bean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {

        return data.size();
    }

    @Override
    public Object getItem(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {
      return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      if (convertView==null){
        convertView= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false);
      }
        TextView tv=convertView.findViewById(R.id.item_tv);
        tv.setText(data.get(position).getSongname());


        Log.e("leo", "getView: "+position);
        return convertView;
    }
}