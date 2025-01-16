package com.example.project;

public class UserAccount extends Account {

    public UserAccount() {
    }
    public UserAccount(String firstName, String lastName, String email, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }
}
