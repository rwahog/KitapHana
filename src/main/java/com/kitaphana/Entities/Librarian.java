package com.kitaphana.Entities;

public class Librarian extends Admin {
  private String type = "Librarian";
  private int privilege = 3;

  public Librarian(Employee employee) {
    super(employee);
  }

  public Librarian(String name, String surname, int privilege, String phone_number,
                   String email, String password, Address address) {
   this.setName(name);
   this.setSurname(surname);
   this.privilege = privilege;
   this.setPhoneNumber(phone_number);
   this.setEmail(email);
   this.setPassword(password);
   this.setAddress(address);
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
