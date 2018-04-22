package com.kitaphana.Entities;

public class Professor extends FacultyMember {

    private int priority;

    public Professor(Patron patron) {
        super(patron);
    }

    @Override
    public void setPriority() {
        this.priority = 4;
    }

}
