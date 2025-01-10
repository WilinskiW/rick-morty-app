package com.rick_morty.rick_morty_data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "episodes")
@Data
public class Episode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "source_id", nullable = false, unique = true)
    private int source_id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "air_date", nullable = false)
    private String airDate;

    @Column(name = "episode", nullable = false)
    private String episode;

    @ManyToMany
    @JoinTable(
            name = "character_episode",
            joinColumns = @JoinColumn(name = "episode_id", nullable = false, unique = true),
            inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false, unique = true)
    )
    private Set<Character> characters;

    @Column(name = "created")
    private LocalDateTime created;
}
