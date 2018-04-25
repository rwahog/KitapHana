package com.kitaphana.Entities;

import java.util.ArrayList;
import java.util.Date;

public class Patron extends User {
  private int priority, maxDays, fine;
  private String possibleType;
  private String documentsId, deadlines, waitingListId,
          renewsId, returnsId, checkoutsId, renewsCount,
          fines;
  private ArrayList<Document> documents, waitingList, renews, returns,
          checkouts;
  private long deadline;

  public Patron() {

  }

  public Patron(String name, String surname, String phone_number, String password,
                String email, Address addressId, String possibleType) {
    super(name, surname, phone_number, password, email, addressId);
    this.possibleType = possibleType;
  }

  public Patron(long id, String name, String surname, String phone_number, String password,
                String email, long addressId, String possibleType, String type, String documentsId,
                String deadlines, String waitingListId, String renewsId, String returnsId,
                String checkoutsId, String renewsCount, String fines) {
    super(id, name, surname, phone_number, password, email, addressId, type);
    this.possibleType = possibleType;
    this.documentsId = documentsId;
    this.deadlines = deadlines;
    this.waitingListId = waitingListId;
    this.renewsId = renewsId;
    this.returnsId = returnsId;
    this.checkoutsId = checkoutsId;
    this.renewsCount = renewsCount;
    this.fines = fines;
  }

  public void setPossibleType(String possibleType) {
    this.possibleType = possibleType;
  }

  public String getPossibleType() {
    return possibleType;
  }

  public void setPriority() {
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }

  public void setMaxDays(int maxDays) {
    this.maxDays = maxDays;
  }

  public int getMaxDays() {
    return maxDays;
  }

  public void setFines(String fines) {
    this.fines = fines;
  }

  public String getFines() {
    return fines;
  }

  public void setDocumentsId(String documentsId) {
    this.documentsId = documentsId;
  }

  public String getDocumentsId() {
    return documentsId;
  }

  public void setDeadlines(String deadlines) {
    this.deadlines = deadlines;
  }

  public String getDeadlines() {
    return deadlines;
  }

  public void setWaitingListId(String waitingListId) {
    this.waitingListId = waitingListId;
  }

  public String getWaitingListId() {
    return waitingListId;
  }

  public void setWaitingList(ArrayList<Document> waitingList) {
    this.waitingList = waitingList;
  }

  public ArrayList<Document> getWaitingList() {
    return waitingList;
  }

  public void setRenewsId(String renewsId) {
    this.renewsId = renewsId;
  }


  public String getRenewsId() {
    return renewsId;
  }

  public void setRenews(ArrayList<Document> renews) {
    this.renews = renews;
  }

  public String getRenewsCount() {
    return renewsCount;
  }

  public void setReturnsId(String returnsId) {
    this.returnsId = returnsId;
  }

  public String getReturnsId() {
    return returnsId;
  }

  public void setReturns(ArrayList<Document> returns) {
    this.returns = returns;
  }

  public ArrayList<Document> getReturns() {
    return returns;
  }

  public void setCheckoutsId(String checkoutsId) {
    this.checkoutsId = checkoutsId;
  }

  public String getCheckoutsId() {
    return checkoutsId;
  }

  public void setCheckouts(ArrayList<Document> checkouts) {
    this.checkouts = checkouts;
  }

  public ArrayList<Document> getCheckouts() {
    return checkouts;
  }

  public void setRenewsNum(String renewsNum) {
    this.renewsCount = renewsNum;
  }

  public String getRenewsNum() {
    return renewsCount;
  }

  public void setDocuments(ArrayList<Document> documents) {
    this.documents = documents;
  }

  public ArrayList<Document> getDocuments() {
    return documents;
  }

  public void setDocumentFine(int fine) {
    this.fine = fine;
  }

  public int getDocumentFine() {
    return fine;
  }

  public void setDocumentDeadline(long deadline) {
    long day = 24 * 60 * 60 * 1000;
    long left;
    Date date = new Date();
    left = deadline - date.getTime();
    this.deadline = (long) Math.ceil((double) left / (double) day);
  }

  public long getDocumentDeadline() {
    return deadline;
  }
}
