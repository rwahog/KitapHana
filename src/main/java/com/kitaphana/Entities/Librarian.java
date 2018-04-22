package com.kitaphana.Entities;

public class Librarian extends Admin {
    private int privilege = 1;

    @Override
    public int getPrivilege() {
        return privilege;
    }
}
