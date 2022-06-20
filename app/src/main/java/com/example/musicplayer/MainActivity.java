package com.example.musicplayer;


import static androidx.constraintlayout.widget.ConstraintLayoutStates.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.musicplayer.Adapter.ViewPagerAdapater;
import com.example.musicplayer.Bean.Bean;
import com.example.musicplayer.Helper.MediaPlayerHelper;
import com.example.musicplayer.Helper.MyDatabaseHelper;
import com.example.musicplayer.Interface.IFragmentCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private IFragmentCallback iFragmentCallback;
    private List<Bean> data=new ArrayList<>();
    //音乐播放器
    MediaPlayerHelper mediaPlayerHelper;
    Button up,pause,next;
    Button back;
    SeekBar sb;
    int Duration;
    int time1,time2;
    String t1,t2;
    Handler handler=new Handler();
    TextView tv1,tv2;
    TextView songLabel;
    //list_view
    TextView item_tv;
    ListView lv1,lv2;
    TextView circle1,circle2;
    //歌曲编号
    int poistion=0;
    //Fragment
    BlankFragment blankFragment;
    BlankFragment2 blankFragment2;
    //viewpager
    ViewPager2 viewPager;
    Bitmap img;
    TextView geci,gequ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayerHelper = MediaPlayerHelper.getInstance(MainActivity.this);

        //初始化
        initTab();
        Log.e("zxy", "onCreate: "+"运行了！");

        //显示画面
        initpager();
    }

    private void initpager() {
        ArrayList<Fragment> fragmentList=new ArrayList<>();

        Intent intent1=getIntent();
        Bundle bundle= this.getIntent().getExtras();
        if (intent1.getExtras()!=null){
            if (mediaPlayerHelper.isPlaying()){
                mediaPlayerHelper.destroy();
                Log.e(TAG, "onCreate:"+"正砸播放" );
            }
            String uri= bundle.getString("song");
//            Log.e(TAG, "歌曲位置 "+uri);
            poistion=bundle.getInt("position");
            startSong(poistion);

        } else {
            startSong(poistion);
        }



        //获取音乐事件长度
//        Duration=mp.getDuration();
        Duration=mediaPlayerHelper.getDuration();
        //拖动条的最大值
        sb.setMax(Duration);
        //拖动条的响应事件
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayerHelper.seekTo(sb.getProgress());
            }
        });



    }

    //通过数据库来查找对应歌曲的位置,并播放
    @SuppressLint("Range")
    private void startSong(int poistion){
        Log.e(TAG, "startSong: "+poistion );
        //连接数据库
        SQLiteOpenHelper helper= MyDatabaseHelper.getmInstance(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        if (db.isOpen()){
            ArrayList<Fragment> fragmentList=new ArrayList<>();
            String songname,song;
            //存储position
            int t=poistion;
            Cursor cursor=db.rawQuery("SELECT * FROM music Where position=="+t,null);
            cursor.moveToFirst();
            //设置歌曲名字
            songname=cursor.getString(cursor.getColumnIndex("songname"));
            songLabel.setText(songname);
            //将歌曲编号存入到mediaHelper中
            mediaPlayerHelper.setPosition(poistion);
            //播放歌曲
            song=cursor.getString(cursor.getColumnIndex("song"));
            //设置歌曲路径
            mediaPlayerHelper.setPath(song);
            handler.post(start);
            //设置歌曲图片
            String songimge=cursor.getString(cursor.getColumnIndex("songimg"));
            blankFragment=new BlankFragment();
            fragmentList.add(BlankFragment.newInstance(songimge));
            //设置歌词
            blankFragment2=new BlankFragment2();
            fragmentList.add(blankFragment2);
//                    viewPager.setOffscreenPageLimit();
            viewPager.setOffscreenPageLimit(fragmentList.size());
            ViewPagerAdapater viewPagerAdapter = new ViewPagerAdapater(getSupportFragmentManager(),getLifecycle(), fragmentList);
            //ViewPager
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @SuppressLint("ResourceAsColor")
                @Override
                public void onPageSelected(int position1) {//歌词和点 亮度变化
                    viewPager.setCurrentItem(position1);
                    switch (position1){
                        case 0:
                            circle1.setBackground(getResources().getDrawable(R.drawable.circle_shape));
                            circle2.setBackground(getResources().getDrawable(R.drawable.circle_shape_active));
                            geci.setTextColor(getResources().getColor(R.color.white));
                            gequ.setTextColor(getResources().getColor(R.color.colorPink));
                            break;
                        case 1:
                            circle1.setBackground(getResources().getDrawable(R.drawable.circle_shape_active));
                            circle2.setBackground(getResources().getDrawable(R.drawable.circle_shape));
                            geci.setTextColor(getResources().getColor(R.color.colorPink));
                            gequ.setTextColor(getResources().getColor(R.color.white));
                            break;
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {


                }
            });
            //关闭游标
            cursor.close();
            db.close();
        }

    }

    private void initTab() {
        //初始化
        circle1=findViewById(R.id.circle1);
        circle2=findViewById(R.id.circle2);
        songLabel=findViewById(R.id.songLabel);
        up=findViewById(R.id.up);
        next=findViewById(R.id.next);
        pause=findViewById(R.id.pause);
        back=findViewById(R.id.back);
        sb=findViewById(R.id.sb);
        tv1=findViewById(R.id.tv1);
        tv2=findViewById(R.id.tv2);
        item_tv=findViewById(R.id.item_tv);
        viewPager=findViewById(R.id.viewpager);
        geci=findViewById(R.id.geci);
        gequ=findViewById(R.id.gequ);
        //监听器设置
        up.setOnClickListener(this);
        next.setOnClickListener(this);
        pause.setOnClickListener(this);
        back.setOnClickListener(this);

    }

    //按钮响应
    @SuppressLint("Range")
    @Override
    public void onClick(View view) {
        //连接数据库
        SQLiteOpenHelper helper= MyDatabaseHelper.getmInstance(this);
        SQLiteDatabase db=helper.getReadableDatabase();
        switch (view.getId()){
            case R.id.pause:
                handler.post(start);
                break;
            case R.id.up:
                mediaPlayerHelper.destroy();
                if (db.isOpen()){
                    ArrayList<Fragment> fragmentList=new ArrayList<>();
                    String songname,song;
                    int t=0;
                    int t1=0;
                    List<Integer> list=new ArrayList<Integer>();
                    t=poistion-1;
                    Cursor cursor1=db.rawQuery("SELECT position FROM music",null);
                    while (cursor1.moveToNext()){
                        t1=cursor1.getInt(cursor1.getColumnIndex("position"));
                        list.add(t1);
                    }
                    t1=Collections.min(list);
                    if (t<t1){
                        t=Collections.max(list);
                    }
                    //存储poistion
                    poistion=t;

                    startSong(poistion);

                    //关闭游标
                    cursor1.close();
                    db.close();
                }
                break;
            case R.id.next:
                ArrayList<Fragment> fragmentList=new ArrayList<>();
                mediaPlayerHelper.destroy();
                if (db.isOpen()){
                    //
                    String songname,song;
                    int t=0;
                    int t1=0;
                    List<Integer> list=new ArrayList<Integer>();
                    t=poistion+1;
                    Cursor cursor1=db.rawQuery("SELECT position FROM music",null);
                    while (cursor1.moveToNext()){
                        t1=cursor1.getInt(cursor1.getColumnIndex("position"));
                        list.add(t1);
                    }
                    if (t>t1){
                        t=0;
                    }
                    poistion=t;
                    startSong(poistion);
                    //关闭游标
                    cursor1.close();
                    db.close();
                }
                break;

            case R.id.back://返回到listview
                //关闭数据库
                db.close();
                startActivity(new Intent(MainActivity.this, Listview_Activity.class));
                break;
        }
    }

    //开始播放
    Runnable start=new Runnable() {
        @Override
        public void run() {
            if (mediaPlayerHelper.isPlaying()){//如果mp正在播放则设置按钮为暂停形状
                pause.setBackgroundResource(R.drawable.icon_play);
                //正在播放点击则暂停
                mediaPlayerHelper.pause();
            }else{
                //如果没有播放则设置为||
                pause.setBackgroundResource(R.drawable.icon_pause);
                mediaPlayerHelper.start();
                //转换时长值
                time2=mediaPlayerHelper.getDuration()/1000;
                t2=time2/60+":"+time2%60;
                tv2.setText(t2);
                handler.post(updatesb);
            }
        }
    };
    //更新进度条、更新歌词

    Runnable updatesb=new Runnable() {
        @Override
        public void run() {
                sb.setProgress(mediaPlayerHelper.getCurrentPosition());
                //转换时长值
                time1=mediaPlayerHelper.getCurrentPosition()/1000;
                t1=time1/60+":"+time1%60;
                tv1.setText(t1);
                handler.postDelayed(updatesb,1000);
        }
    };

    public MainActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: " );
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: " );
    }
}