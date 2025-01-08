package com.rick_morty.rick_morty_data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "characters")
@Data
public class Character {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    @Column(name = "species")
    private String species;

    @Column(name = "type")
    private String type;

    @Column(name = "gender")
    private String gender;

    @ManyToOne
    @JoinColumn(name = "origin_id")
    private Location origin;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "image")
    private String imageUrl;

    @Column(name = "created")
    private LocalDateTime created;

    @ManyToMany(mappedBy = "characters")
    private Set<Episode> episodes;
}
