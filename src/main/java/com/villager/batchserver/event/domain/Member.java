package com.villager.batchserver.event.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @ElementCollection
    @CollectionTable(
            name = "member_tag",
            joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "name")
    private List<String> tags = new ArrayList<>();
    private boolean isDeleted;
}
