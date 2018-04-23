package com.kitaphana.Entities;

public class JournalArticle extends Document {
  private String journalName, date, title, editors;
  private long id, documentId;

  public JournalArticle(String title, int price, int amount, String type,
                        String description, String journalName,
                        String date, String editors) {
    super(title, price, amount, type, description);
    this.journalName = journalName;
    this.date = date;
    this.title = title;
    this.editors = editors;
  }

  public JournalArticle() {
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getId() {
    return id;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setJournalName(String journal_name) {
    this.journalName = journal_name;
  }

  public String getJournalName() {
    return journalName;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getDate() {
    return date;
  }

  public void setEditors(String editors) {
    this.editors = editors;
  }

  public String getEditors() {
    return editors;
  }

  public void setDocumentId(long document_id) {
    this.documentId = document_id;
  }

  public long getDocumentId() {
    return documentId;
  }

}
