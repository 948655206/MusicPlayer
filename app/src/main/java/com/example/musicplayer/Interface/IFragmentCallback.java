package com.example.musicplayer.Interface;

public interface IFragmentCallback {
    //发送接口
    void sendMessageToActivity(int currentPosition);
    //接收接口
    int getMsgFromActivity(int currentPosition);
}
