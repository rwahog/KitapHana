package com.kitaphana.Entities;

public class Admin extends User {
    private int privilege = 0;

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public int getPrivilege() {
        return privilege;
    }
}
