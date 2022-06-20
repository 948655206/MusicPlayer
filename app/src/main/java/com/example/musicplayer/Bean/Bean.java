package com.example.musicplayer.Bean;

import org.w3c.dom.Text;

public class Bean {
  private String Songname;

  public String getSongimage() {
    return Songimage;
  }

  public void setSongimage(String songimage) {
    Songimage = songimage;
  }

  private String Songimage;
  public String getSongs() {
    return songs;
  }

  public void setSongs(String songs) {
    this.songs = songs;
  }

  private String songs;
  private int getPosition() {
    return position;
  }

  public void setPosition(int position) {
    this.position = position;
  }

  private int position;
  public String getSongname() {
    return Songname;
  }

  public void setSongname(String songname) {

    this.Songname = songname;

  }





}