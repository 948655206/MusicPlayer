package com.example.musicplayer.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;

public class MediaPlayerHelper {

    @SuppressLint("StaticFieldLeak")
    private static MediaPlayerHelper instance;

    private final Context mContext;//上下文对象
    private MediaPlayer mMediaPlayer;//MediaPlayer媒体类
    private String mPath;//歌曲路径
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public static MediaPlayerHelper getInstance(Context context) {

        if (instance == null) {
            synchronized (MediaPlayerHelper.class) {
                if (instance == null) {
                    instance = new MediaPlayerHelper(context);
                }
            }
        }

        return instance;
    }

    private MediaPlayerHelper(Context context) {
        mContext = context.getApplicationContext();
        mMediaPlayer = new MediaPlayer();
    }

//    private OnMeidaPlayerHelperListener onMeidaPlayerHelperListener;//媒体监听
//
//    /**
//     * 媒体监听接口，播放准备和播放完成
//     */
//    public interface OnMeidaPlayerHelperListener {
//        void onPrepared(MediaPlayer mp);
//        void onCompletion(MediaPlayer mp);
//    }
//
//    public void setOnMeidaPlayerHelperListener(OnMeidaPlayerHelperListener onMeidaPlayerHelperListener) {
//        this.onMeidaPlayerHelperListener = onMeidaPlayerHelperListener;
//    }

    /**
     * 设置当前需要播放的音乐
     */
    public void setPath (String path) {
        mPath = path;

        // 设置播放音乐路径
        mMediaPlayer=MediaPlayer.create(mContext, Uri.parse(path));

    }

    /**
     * 返回正在播放的音乐路径
     */
    public String getPath () {
        return mPath;
    }


    /**
     * 播放音乐
     */
    public void start () {
        if (mMediaPlayer.isPlaying()) return;
        mMediaPlayer.start();
    }

    /**
     * 返回正在播放的音乐路径AudioSessionId
     */
    public int getAudioSessionId() {
        return mMediaPlayer.getAudioSessionId();
    }

    /**
     * 暂停播放
     */
    public void pause () {
        mMediaPlayer.pause();
    }

    /**
     * 是否正在播放
     */
    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    /**
     * 获取媒体播放的位置（进度条）
     */
    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 获取媒体播放的总时长
     */
    public int getDuration(){
        return mMediaPlayer.getDuration();
    }

    /**
     * 定位到媒体播放进度
     */
    public void seekTo(int progress){
        mMediaPlayer.seekTo(progress);
    }

    //销毁音乐
    public void destroy(){
        mMediaPlayer.pause();
        mMediaPlayer.stop();
        mMediaPlayer.release();//清除缓存
    }

}