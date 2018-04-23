package com.kitaphana.Entities;

public class Employee extends User {

  private int privilege = -1;

  public Employee(Employee employee) {
    super(employee.getId(), employee.getName(), employee.getSurname(),
            employee.getPhoneNumber(), employee.getPassword(),
            employee.getEmail(), employee.getAddressId());
  }

  public void setPrivilege(int privilege) {
    this.privilege = privilege;
  }

  public int getPrivilege() {
    return privilege;
  }

  public Employee() {

  }
}
