package com.rick_morty.rick_morty_data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "locations")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "dimension")
    private String dimension;

    @Column(name = "created")
    private LocalDateTime created;

    @OneToMany(mappedBy = "origin")
    private Set<Character> originCharacters;

    @OneToMany(mappedBy = "location")
    private Set<Character> currentCharacters;
}
