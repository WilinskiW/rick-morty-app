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

    @Column(name = "source_id", nullable = false, unique = true)
    private int source_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "species", nullable = false)
    private String species;

    @Column(name = "type")
    private String type;

    @Column(name = "gender", nullable = false)
    private String gender;

    @ManyToOne
    @JoinColumn(name = "origin_id")
    private Location origin;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @Column(name = "image", nullable = false)
    private String imageUrl;

    @Column(name = "created", nullable = false)
    private LocalDateTime created;

    @ManyToMany(mappedBy = "characters")
    @Column(name = "episodes", nullable = false)
    private Set<Episode> episodes;
}
