package com.example.musicplayer.Bean;

public class LrcBean {
  //歌词
  private String lrc;
  //开始时间
  private long start;
  //结束时间
  private long end;

  public String getTranslateLrc() {
    return translateLrc;
  }

  public void setTranslateLrc(String translateLrc) {
    this.translateLrc = translateLrc;
  }

  private String translateLrc;
  public LrcBean(String lrc,long start){
    this.lrc=lrc;
    this.start=start;
  }

  public String getLrc() {
    return lrc;
  }

  public void setLrc(String lrc) {
    this.lrc = lrc;
  }

  public long getStart() {
    return start;
  }

  public void setStart(long start) {
    this.start = start;
  }

  public long getEnd() {
    return end;
  }

  public void setEnd(long end) {
    this.end = end;
  }
}