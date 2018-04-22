package com.kitaphana.Entities;

public class AVMaterial extends Document {
    private String title;
    private long docId;

    public AVMaterial(String title, int price,
                      int amount, String type, String description) {
        super(title, price, amount, type, description);
        this.title = title;
    }

    public AVMaterial() {
    }
    
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setDocumentId(long docId) {
        this.docId = docId;
    }

    public long getDocumentId() {
        return docId;
    }
}
