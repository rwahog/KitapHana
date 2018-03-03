package com.kitaphana.Entities;

public class AVMaterial extends Document {
    protected String title;
    
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }
}
