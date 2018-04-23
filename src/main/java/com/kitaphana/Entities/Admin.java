package com.kitaphana.Entities;

public class Admin extends Employee {
    private int privilege = 0;
    private String type = "Admin";

    public Admin(Employee employee) {
        super(employee);
    }

    public int getPrivilege() {
        return privilege;
    }

    @Override
    public String getType() {
        return type;
    }
}
