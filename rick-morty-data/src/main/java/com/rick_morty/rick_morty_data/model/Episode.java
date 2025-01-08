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

    @Column(name = "name")
    private String name;

    @Column(name = "air_date")
    private String airDate;

    @Column(name = "episode")
    private String episode;

    @Column(name = "created")
    private LocalDateTime created;

    @ManyToMany
    @JoinTable(
            name = "character_episode",
            joinColumns = @JoinColumn(name = "episode_id"),
            inverseJoinColumns = @JoinColumn(name = "character_id")
    )
    private Set<Character> characters;
}
