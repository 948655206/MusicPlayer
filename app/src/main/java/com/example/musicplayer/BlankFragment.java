package com.example.musicplayer;

import static com.example.musicplayer.R.raw.saybye;
import static com.example.musicplayer.R.raw.youcanhear;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.musicplayer.Adapter.MyAdapter;
import com.example.musicplayer.Bean.Bean;

import java.util.ArrayList;
import java.util.List;


public class BlankFragment extends Fragment {
    private static final String param1 = "param1";
    private View view;
    private List<Bean> data=new ArrayList<>();
    private String mTextString;
    View rootview;
    public static BlankFragment newInstance(String param1) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString("img",param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTextString= getArguments().getString("img");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootview==null){
            rootview=inflater.inflate(R.layout.fragment_blank, container, false);
        }
        initView();
        return rootview;
    }
    //设置图片
    private void initView() {
        Bitmap bitmap= BitmapFactory.decodeFile(mTextString);
        ImageView imageView=rootview.findViewById(R.id.image1);
        imageView.setImageBitmap(bitmap);
    }
}