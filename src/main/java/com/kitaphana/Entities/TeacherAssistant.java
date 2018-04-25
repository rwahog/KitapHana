package com.kitaphana.Entities;

public class TeacherAssistant extends FacultyMember {

  private int priority;

  public TeacherAssistant(Patron patron) {
    super(patron);
    this.setType("TeacherAssistant");
  }

  @Override
  public void setPriority() {
    this.priority = 3;
  }

}
