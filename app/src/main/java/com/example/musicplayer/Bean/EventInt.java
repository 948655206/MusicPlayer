package com.example.musicplayer.Bean;

public class EventInt {
  public int currentPositions;

  public EventInt(int currentPosition){

    this.currentPositions=currentPosition;
  }

  public int getCurrentPosition() {
    currentPositions=123;
    return currentPositions;
  }



  public void setCurrentPosition(int currentPosition)  {

    this.currentPositions = currentPosition;
  }
}