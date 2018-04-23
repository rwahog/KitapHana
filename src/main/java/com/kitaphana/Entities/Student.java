package com.kitaphana.Entities;

public class Student extends Patron {

  private int priority = 1;
  private int maxDays = 21;

  public Student(Patron patron) {
    super(patron.getId(), patron.getName(), patron.getSurname(), patron.getPhoneNumber(),
            patron.getPassword(), patron.getEmail(), patron.getAddressId(),
            patron.getPossibleType(), patron.getDocumentsId(), patron.getDeadlines(),
            patron.getWaitingListId(), patron.getRenewsId(), patron.getReturnsId(),
            patron.getCheckoutsId(), patron.getRenewsCount(), patron.getFines());
  }

  @Override
  public int getPriority() {
    return priority;
  }
}
