package com.kitaphana.Service;

public class Book extends Document {
    protected String publisher;
    protected int year, edition_number;
    protected int best_seller;

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
}
