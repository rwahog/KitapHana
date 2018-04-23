package com.kitaphana.Entities;

public class ActionMessage {

  private String action, date;

  public ActionMessage(String date, String action) {
    this.action = action;
    this.date = date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getDate() {
    return date;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getAction() {
    return action;
  }
}
