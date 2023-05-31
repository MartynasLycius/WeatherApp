package com.proit.application.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "application_user")
@EqualsAndHashCode(callSuper = true)
public class User extends AbstractEntity implements Serializable {
    @Column(name = "username")
    private String username;

    @Column(name = "name")
    private String name;

    @Column(name = "hashed_password")
    private String hashedPassword;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserRole> roles;
}
