package com.kitaphana.Entities;

class FacultyMember extends Patron {

  private int maxDays = 28;

  FacultyMember(Patron patron) {
    super(patron.getId(), patron.getName(), patron.getSurname(), patron.getPhoneNumber(),
            patron.getPassword(), patron.getEmail(), patron.getAddressId(),
            patron.getPossibleType(), patron.getType(), patron.getDocumentsId(),
            patron.getDeadlines(), patron.getWaitingListId(), patron.getRenewsId(),
            patron.getReturnsId(), patron.getCheckoutsId(), patron.getRenewsCount(),
            patron.getFines());
  }

}
