package com.weatherapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 11 Jun, 2024
 */

@Entity(name = "user")
@Table(name="user"
        ,catalog="weather"
        , uniqueConstraints = @UniqueConstraint(columnNames={"user_name"})
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Column(name = "user_name")
    private String userName;

    @NotBlank
    @Column(name = "password")
    private String password;

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
