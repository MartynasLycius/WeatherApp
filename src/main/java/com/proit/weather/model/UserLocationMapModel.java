package com.proit.weather.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "UserLocationMapModel")
@Table(name = "user_locations")
public class UserLocationMapModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_UserLocation_User", value = ConstraintMode.NO_CONSTRAINT))
    private User userInfo;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "location_info_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_UserLocation_Location", value = ConstraintMode.NO_CONSTRAINT))
    private LocationInfoModel locationInfoModel;
}
