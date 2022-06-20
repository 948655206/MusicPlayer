package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.musicplayer.Adapter.MyAdapter;
import com.example.musicplayer.Bean.Bean;
import com.example.musicplayer.Helper.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class Listview_Activity extends AppCompatActivity {
    private List<Bean> data=new ArrayList<>();
    //音乐播放器
    MediaPlayer mp;
    ListView lv1;
    int poisition;
    String songname;
    String song;
    String songimage;
    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        //初始化
        lv1=findViewById(R.id.lv1);

        //连接数据库
        SQLiteOpenHelper helper= MyDatabaseHelper.getmInstance(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        if (db.isOpen()){
            Cursor cursor=db.rawQuery("SELECT * FROM music",null);
            while (cursor.moveToNext()){
                //Bean从sql中读取歌曲位置，歌名，文件，图片，歌词
                // 通过position创建多个bean显示在listview中
                poisition=cursor.getInt(cursor.getColumnIndex("position"));
                songname=cursor.getString(cursor.getColumnIndex("songname"));
                song=cursor.getString(cursor.getColumnIndex("song"));
                songimage=cursor.getString(cursor.getColumnIndex("songimg"));
                Log.e("Tag", "onCreate: "+songname);
                //通过Bean来设置歌曲名字,序号,存放位置
                Bean bean=new Bean();
                bean.setSongname(songname);
                bean.setPosition(poisition);
                bean.setSongs(song);
                bean.setSongimage(songimage);
                data.add(bean);
            }
            //关闭游标
            cursor.close();
        }

        //利用BaseAdapter将data传入到listview中
        lv1.setAdapter(new MyAdapter(data,this));
        //listview监听
        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String songname=lv1.getItemAtPosition(position).toString();
                //通过T获取List_view中点击的位置
                int t=position;
                Log.e("TAG", "onItemClick123:"+t);
                songname=data.get(t).getSongname();
                song=data.get(t).getSongs();
                songimage=data.get(t).getSongimage();
                //将得到的音乐名字和位置传回给播放界面
                Intent intent=new Intent(Listview_Activity.this,MainActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("song", songname);
                bundle.putInt("position",position);
                bundle.putString("song",song);
                bundle.putString("songimage",songimage);
                //发送数据给MainActivity
                intent.putExtras(bundle);
                startActivity(intent);
                Log.e("TAG", "zxy123: "+songname+song);
//                if (db.isOpen()){
//                    Cursor cursor=db.rawQuery("SELECT * FROM music Where position=="+t,null);
//                    cursor.moveToFirst();
//                    songname=cursor.getString(cursor.getColumnIndex("songname"));
//                    song=cursor.getString(cursor.getColumnIndex("song"));
//                    Log.e("Tag", "onItenClickListener: "+songname+position);
//                    //将得到的音乐名字和位置传回给播放界面
//                    Intent intent=new Intent(Listview_Activity.this,MainActivity.class);
//                    Bundle bundle=new Bundle();
//                    bundle.putString("song", songname);
//                    bundle.putInt("position",position);
//                    bundle.putString("song",song);
//                    //发送数据给MainActivity
//                    intent.putExtras(bundle);
//                    startActivity(intent);
//                    //关闭游标
//                    cursor.close();
//                    db.close();
//                }
//                switch (position){//根据点击某首歌的id来播放
//                    case 0:
//                            Intent intent=new Intent(Listview_Activity.this,MainActivity.class);
//                            Bundle bundle=new Bundle();
//
//                            bundle.putInt("songname", youcanhear);
//                            bundle.putInt("position",position);
//                            //发送数据给MainActivity
//                            intent.putExtras(bundle);
//                            startActivity(intent);
//                            break;
//                    case 1:
//                            Bundle bundle2=new Bundle();
//                            bundle2.putInt("songname", saybye);
//                            bundle2.putInt("position",position);
//                            //发送数据给MainActivity
//                            Intent intent1=new Intent(Listview_Activity.this,MainActivity.class).putExtras(bundle2);
//                            startActivity(intent1);
//                            break;
//                }
                Log.e("歌曲名字:", "onItemClick: "+songname+position+song );
            }
        });

    }
}