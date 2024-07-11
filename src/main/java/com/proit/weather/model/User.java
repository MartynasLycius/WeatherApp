package com.proit.weather.model;

import com.proit.weather.enums.RoleType;
import com.proit.weather.utils.AES;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "User")
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames={"username"}))
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @Email
    @NotEmpty
    private String email;

    @Enumerated(value = EnumType.STRING)
    private RoleType roleType;

    public void setEncryptedPassword(String password) {
        this.password = AES.encrypt(password);
    }
}
