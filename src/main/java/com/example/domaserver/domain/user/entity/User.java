package com.example.domaserver.domain.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "UUID4")
    private UUID id;

    private String name;

    private String email;

    @Embedded
    private StudentNum studentNum;

    @Enumerated(EnumType.STRING)
    private Authority authority;
}
