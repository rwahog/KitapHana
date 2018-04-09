package com.kitaphana.Entities;

public class Book extends Document {
    private String publisher;
    private int year, editionNumber;
    private int bestseller;
    private long documentId;

    public Book(String publisher, int year, int edition_number, int bestseller) {
        this.publisher = publisher;
        this.year = year;
        this.editionNumber = edition_number;
        this.bestseller = bestseller;
    }

    public Book(String title, String authors, String keywords, int price, int amount,
                String type, String description, String publisher,
                int year, int editionNumber, int bestseller) {
        super(title, authors, keywords, price, amount, type, description);
        this.publisher = publisher;
        this.year = year;
        this.editionNumber = editionNumber;
        this.bestseller = bestseller;
    }

    public Book(){
    }

    public void setEditionNumber(int editionNumber) {
        this.editionNumber = editionNumber;
    }

    public void setBestseller(int bestseller) {
        this.bestseller = bestseller;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setDocumentId(long documentId) {
        this.documentId = documentId;
    }

    public int getEditionNumber() {
        return editionNumber;
    }

    public int getYear() {
        return year;
    }

    public String getPublisher() {
        return publisher;
    }

    public int isBestseller() {
        return bestseller;
    }

    public long getDocumentId() {
        return documentId;
    }
}
