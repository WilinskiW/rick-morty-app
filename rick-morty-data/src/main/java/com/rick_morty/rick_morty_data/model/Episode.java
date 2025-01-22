package com.rick_morty.rick_morty_data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "episodes")
@Data
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "source_id")
    private int sourceId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "air_date", nullable = false)
    private String airDate;

    @Column(name = "episode", nullable = false)
    private String episode;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "character_episode",
            joinColumns = @JoinColumn(name = "episode_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false)
    )
    private Set<Character> characters = new HashSet<>();

    @Column(name = "created")
    private LocalDateTime created = LocalDateTime.now();
}
