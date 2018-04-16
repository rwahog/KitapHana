package com.kitaphana.Entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class User {
    private String phoneNumber, name, surname, password, possibleType, type,
                   email, documents, deadlines, waitingList, renews, checkouts,
                   returns, fine;
    private Address address;
    private ArrayList<Document> documentsArray;
    private long cardNumber, maxDays, id, addressId;
    public long deadline, chatId;
    private int priority;
    private int privilege;

    public User(String name, String surname, String phone_number, String password,
                String email, Address address, String possibleType) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phone_number;
        this.password = password;
        this.email = email;
        this.possibleType = possibleType;
        this.cardNumber = setCardNumber(phone_number);
        this.address = address;
    }

    public User() {
    }

    public User(String name, String surname, String phone_number, String password,
                String email, Address address) {
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phone_number;
        this.password = password;
        this.email = email;
        this.cardNumber = setCardNumber(phone_number);
        this.address = address;
    }
    
    public void setId(long id) {
        this.id = id;
    }

    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getSurname() {
        return surname;
    }

    public void setCardNumber(long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public long getCardNumber() {
        return cardNumber;
    }

    public void setPossibleType(String possibleType) {
        this.possibleType = possibleType;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDocumentsArray(ArrayList<Document> documentsArray) {
        this.documentsArray = documentsArray;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public void setDeadlines(String deadlines) {

        this.deadlines = deadlines;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public void setRenews(String renews) {
        this.renews = renews;
    }

    public void setReturns(String returns) {
        this.returns = returns;
    }


    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getChatId() {
        return chatId;
    }

    public void setPriority() {
        switch (type) {
            case "Student":
                this.priority = 1;
                break;
            case "Instructor":
                this.priority = 2;
                break;
            case "Teacher Assistant":
                this.priority = 3;
                break;
            case "Visiting Professor":
                this.priority = 4;
                break;
            case "Professor":
                this.priority = 5;
                break;
        }

    }

    public void setMaxDays() {
        switch (type) {
            case "Student":
                this.maxDays = 21;
                break;
            case "Visiting Professor":
                this.maxDays = 7;
                break;
            default:
                this.maxDays = 28;
        }
    }

    public void setDeadline(long deadline) {
        this.deadline = deadline;
    }

    public void setCheckouts(String checkouts) {
        this.checkouts = checkouts;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public void setWaitingList(String waitingList) {
        this.waitingList = waitingList;
    }

    public long getId() {
        return id;
    }

    public String getPossibleType() {
        return possibleType;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public long getAddressId() {
        return addressId;
    }

    public String getDeadlines() {
        return deadlines;
    }

    public int getPriority() {
        return priority;
    }

    public String getType() {
        return type;
    }

    public String getDocuments() {
        return documents;
    }

    public String getRenews() {
        return renews;
    }

    public String getFine() {
        return fine;
    }

    public String getReturns() {
        return returns;
    }

    public long getMaxDays() {
        return maxDays;
    }

    public String getWaitingList() {
        return waitingList;
    }

    public String getCheckouts() {
        return checkouts;
    }

    public int getPrivilege() {
        return privilege;
    }

    public ArrayList<Document> getDocumentsArray() {
        return documentsArray;
    }

    public long setCardNumber(String phone_number) {
        long p = 31, hash = 0, mod = 1000000007;
        for (int i = 0; i < phone_number.length(); i++) {
            hash *= p;
            hash %= mod;
            hash += phone_number.charAt(i);
            hash %= mod;
        }
        return hash;
    }
}
