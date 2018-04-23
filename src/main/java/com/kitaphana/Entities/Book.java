package com.kitaphana.Entities;

public class Book extends Document {
  private String publisher;
  private int year, editionNumber, bestseller;
  private long documentId;

  public Book(String publisher, int year, int edition_number, int bestseller) {
    this.publisher = publisher;
    this.year = year;
    this.editionNumber = edition_number;
    this.bestseller = bestseller;
  }

  public Book(String title, int price, int amount,
              String type, String description, String publisher,
              int year, int editionNumber, int bestseller) {
    super(title, price, amount, type, description);
    this.publisher = publisher;
    this.year = year;
    this.editionNumber = editionNumber;
    this.bestseller = bestseller;
  }

  public Book() {
  }

  public void setEditionNumber(int editionNumber) {
    this.editionNumber = editionNumber;
  }

  public int getEditionNumber() {
    return editionNumber;
  }

  public void setBestseller(int bestseller) {
    this.bestseller = bestseller;
  }

  public int isBestseller() {
    return bestseller;
  }

  public void setPublisher(String publisher) {
    this.publisher = publisher;
  }

  public String getPublisher() {
    return publisher;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public int getYear() {
    return year;
  }

  public void setDocumentId(long documentId) {
    this.documentId = documentId;
  }

  public long getDocumentId() {
    return documentId;
  }
}
