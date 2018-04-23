package com.kitaphana.Entities;

public class Librarian extends Admin {
    private String type = "Librarian";
    private int privilege = 3;
    public Librarian(Employee employee) {
        super(employee);
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    @Override
    public int getPrivilege() {
        return privilege;
    }

    @Override
    public String getType() {
        return type;
    }
}
