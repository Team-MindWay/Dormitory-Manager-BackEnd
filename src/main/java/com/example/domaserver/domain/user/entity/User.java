package com.example.domaserver.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "UUID4")
    @Column(length = 36) 
    private UUID id;

    private String username;
    private String email;
    private String password;

    @Embedded
    private StudentNum studentNum;

    @Enumerated(EnumType.STRING)
    private Authority authority;
}
