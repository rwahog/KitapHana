package com.kitaphana.Entities;

public class AVMaterial extends Document {
    protected String title;

    public AVMaterial(String title, String authors, String keywords, int price,
                      int amount, String type, String cover, String description) {
        super(title, authors, keywords, price, amount, type, description);
        this.title = title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
