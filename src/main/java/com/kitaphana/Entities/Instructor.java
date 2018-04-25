package com.kitaphana.Entities;

public class Instructor extends FacultyMember {

  private int priority;

  public Instructor(Patron patron) {
    super(patron);

    this.setType("Instructor");
  }

  @Override
  public void setPriority() {
    this.priority = 2;
  }

}
