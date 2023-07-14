package com.example.luyen_tap_1.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "people")
public class People {
    @Id
    private String id;
    private String first_name;
    private String last_name;
    private String email;
    private String gender;
    private String ip_address;
    private String avatar;
    private String country;
    private String job;
    private String company;
    private String salary;
    private String username;
    private String password;
    private String slogan;
}
