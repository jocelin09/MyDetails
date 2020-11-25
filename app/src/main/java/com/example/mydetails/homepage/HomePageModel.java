package com.example.mydetails.homepage;

public class HomePageModel {
    String firstname,lastname,designation;
    byte[] image;
    
public HomePageModel(String firstname, String lastname, String designation, byte[] image) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.designation = designation;
    this.image = image;
}

public HomePageModel(String firstname, String lastname, String designation) {
    this.firstname = firstname;
    this.lastname = lastname;
    this.designation = designation;
}


public String getFirstname() {
    return firstname;
}

public void setFirstname(String firstname) {
    this.firstname = firstname;
}

public String getLastname() {
    return lastname;
}

public void setLastname(String lastname) {
    this.lastname = lastname;
}

public String getDesignation() {
    return designation;
}

public void setDesignation(String designation) {
    this.designation = designation;
}

public byte[] getImage() {
    return image;
}

public void setImage(byte[] image) {
    this.image = image;
}
}
