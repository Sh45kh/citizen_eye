package com.graduation.project.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // This Lombok annotation magically creates your getters and setters!
@Table(name = "users") // We name it "users" because "user" is a reserved keyword in Postgres
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String fullName;
}