package com.example.application.data;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
@Entity
@Table(name = "users")
public class Users extends AbstractEntity {
    private String username;
    private String password;

    public Users() {

    }
    public Users(String username, String password) {
        this.username = username;
        this.password = password;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
