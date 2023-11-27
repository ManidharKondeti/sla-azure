package com.example.azureSLA.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//@Entity
//@Table(name="Users")
public class Users {
    
    @Id
    private int userId;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String phone;
     private String email;
    private String pswrd;
    private boolean isAdmin;
    private int fine;
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPswrd() {
        return pswrd;
    }
    public void setPswrd(String pswrd) {
        this.pswrd = pswrd;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    public int getFine() {
        return fine;
    }
    public void setFine(int fine) {
        this.fine = fine;
    }

    
}
