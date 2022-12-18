package com.villager.batchserver.event.domain;

import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
public class Town {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "town_id")
    private Long id;
    @Column(nullable = false, length = 10)
    private String code;
}
