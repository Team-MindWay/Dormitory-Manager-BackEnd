package com.example.domaserver.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "UUID4")
    private UUID id;

    private String name;

    private String email;

    private String to_day_clean;

    private int penalty_point;
    private int clean_point;
    private int rank;

    @Embedded
    private StudentNum studentNum;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    public User(UUID id, String name, String to_day_clean,
                int penalty_point, int clean_point, int rank) {
        this.id = id;
        this.name = name;
        this.to_day_clean = to_day_clean;
        this.penalty_point = penalty_point;
        this.clean_point = clean_point;
        this.rank = rank;
        this.studentNum = new StudentNum();
    }

    public int getPenaltyPoint() {
        return penalty_point;
    }
    public void setPenaltyPoint(int penalty_point) {
        this.penalty_point = penalty_point;
    }

    public int getCleanPoint() {
        return clean_point;
    }
    public void setCleanPoint(int clean_point) {
        this.clean_point = clean_point;
    }

    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }




}
