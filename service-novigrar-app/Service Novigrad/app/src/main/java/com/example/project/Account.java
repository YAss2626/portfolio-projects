package com.example.project;

import java.util.List;

public abstract class Account {

    String firstName;
    String lastName;
    String email;
    String password;

    void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    void setLastName(String lastName) {
        this.lastName = lastName;
    }
    void setEmail(String email) {
        this.email = email;
    }
    void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }



    //test pour github
    
}


