package com.proit.application.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Entity
@Table(name = "user_fav_location")
@EqualsAndHashCode(callSuper = true)
public class UserFavLocation extends AbstractEntity implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "location_id")
    private Long locationId;
}
