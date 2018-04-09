package com.kitaphana.Entities;

import java.util.ArrayList;
import java.util.Arrays;

public class Keyword {
    protected long id;
    protected String keyword, documentsId;

    public Keyword() {

    }

    public Keyword(String keyword) {
        this.keyword = keyword;
    }


    public void setId(long id) {
        this.id = id;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setDocumentsId(String documentsId) {
        this.documentsId = documentsId;
    }

    public long getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getDocumentsId() {
        return documentsId;
    }
}
