package com.example.musicplayer;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.musicplayer.Bean.LrcBean;
import com.example.musicplayer.Helper.MediaPlayerHelper;
import com.example.musicplayer.Helper.MyDatabaseHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class BlankFragment2 extends Fragment {
    private LrcView lrcView;
    private View rootview;
    MediaPlayerHelper mediaPlayerHelper;



    @Override
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mediaPlayerHelper = MediaPlayerHelper.getInstance(getActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootview==null){
            rootview=inflater.inflate(R.layout.fragment_blank2, container, false);
        }
        lrcView=rootview.findViewById(R.id.zxy123);
        initLrc();
        Log.e("Tag", "onCreateView:123 ");
        return rootview;
    }



    @Override
    public void onResume() {
        super.onResume();

    }

    //设置歌词
    @SuppressLint("Range")
    private void initLrc() {
        String songpath;
        //连接数据库
        SQLiteOpenHelper helper= MyDatabaseHelper.getmInstance(getContext());
        SQLiteDatabase db=helper.getReadableDatabase();
        if (db.isOpen()){
            int id=mediaPlayerHelper.getPosition();
            Log.e("Tag", "initLrc: "+id);
            Cursor cursor=db.rawQuery("SELECT * FROM music Where position=="+id,null);
            cursor.moveToFirst();
            songpath=cursor.getString(cursor.getColumnIndex("songpath"));

            lrcView.setMusicService(mediaPlayerHelper);
            //获取绝对路径
            String lrcAbsPath=LrcUtil.getLrcFilePath(songpath);
            //获取路径中的内容
            String lrcstr=LrcUtil.parseLrcFile(lrcAbsPath);
            //对内容中的数据处理
            List<LrcBean> lrcBeans=LrcUtil.parseStr2List(lrcstr);
            lrcView.MatchesLrcBeans(lrcBeans);
            lrcView.init();

            //关闭游标
            cursor.close();
            db.close();
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}