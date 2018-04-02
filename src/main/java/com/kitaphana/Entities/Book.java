package com.kitaphana.Entities;

public class Book extends Document {
    protected String publisher;
    protected int year, edition_number;
    protected int best_seller;
    protected long id_document;

    public Book(String publisher, int year, int edition_number, int bestseller) {
        this.publisher = publisher;
        this.year = year;
        this.edition_number = edition_number;
        this.best_seller = bestseller;
    }

    public Book(String title, String authors, String keywords, int price, int amount,
                String type, String description, String publisher,
                int year, int edition_number, int bestseller) {
        super(title, authors, keywords, price, amount, type, description);
        this.publisher = publisher;
        this.year = year;
        this.edition_number = edition_number;
        this.best_seller = bestseller;
    }

    public Book(){
    }

    public void setEdition_number(int edition_number) {
        this.edition_number = edition_number;
    }

    public void setBest_seller(int best_seller) {
        this.best_seller = best_seller;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setId_document(long id_document) {
        this.id_document = id_document;
    }

    public int getEdition_number() {
        return edition_number;
    }

    public int getYear() {
        return year;
    }

    public String getPublisher() {
        return publisher;
    }

    public int isBest_seller() {
        return best_seller;
    }

    public long getId_document() {
        return id_document;
    }
}
