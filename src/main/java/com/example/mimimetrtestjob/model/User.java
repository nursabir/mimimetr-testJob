package com.example.mimimetrtestjob.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

    @Id()
    @Column(name = "email")
    private String email;


}
