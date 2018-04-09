package com.kitaphana.Entities;

public class AVMaterial extends Document {
    private String title;
    private long docId;

    public AVMaterial(String title, String authors, String keywords, int price,
                      int amount, String type, String cover, String description) {
        super(title, authors, keywords, price, amount, type, description);
        this.title = title;
    }

    public AVMaterial() {

    }
    
    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public void setDocumentId(long docId) {
        this.docId = docId;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public long getDocumentId() {
        return docId;
    }
}
