package com.kitaphana.Entities;

public class VisitingProfessor extends Patron {

  private int priority = 5;
  private int maxDays = 7;

  public VisitingProfessor(Patron patron) {
    super(patron.getId(), patron.getName(), patron.getSurname(), patron.getPhoneNumber(),
            patron.getPassword(), patron.getEmail(), patron.getAddressId(),
            patron.getPossibleType(), patron.getType(), patron.getDocumentsId(), patron
            .getDeadlines(),
            patron.getWaitingListId(), patron.getRenewsId(), patron.getReturnsId(),
            patron.getCheckoutsId(), patron.getRenewsCount(), patron.getFines());
  }

  @Override
  public int getPriority() {
    return priority;
  }
}
