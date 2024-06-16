package com.example.model;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    @Column(name = "user_name")
    private String username;
    @Column(name = "local_date")
    private LocalDate registrationDate;
    @Column(name = "email")
    private String email;
    @Column(name = "level")
    private int level;
    @Column(name = "active")
    private boolean active;

    public User() {
    }

    public User(String username, LocalDate registrationDate){
        this.username=username;
        this.registrationDate=registrationDate;
    }
}
