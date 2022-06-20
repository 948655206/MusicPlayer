package com.example.musicplayer.Helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
  //提供对外函数
  private static SQLiteOpenHelper mInstance;
  public static SQLiteOpenHelper getmInstance(Context context) {
    if (mInstance==null){
      mInstance=new MyDatabaseHelper(context, "Music.db",null,2);
    }
    return mInstance;
  }
  //设置构造函数，因为数据库必须有数据库名字、版本号等，通过此函数来传递，完成数据库的创建
  public MyDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
    super(context, name, factory, version);
  }


  @Override
  public void onCreate(SQLiteDatabase db) {

  }
  //更新数据库
  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

  }

}