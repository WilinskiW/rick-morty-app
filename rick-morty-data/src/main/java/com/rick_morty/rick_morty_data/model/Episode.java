package com.rick_morty.rick_morty_data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;
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

    @Column(name = "name")
    private String name;

    @Column(name = "air_date")
    private String airDate;

    @Column(name = "episode")
    private String episode;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(
            name = "character_episode",
            joinColumns = @JoinColumn(name = "episode_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "character_id", nullable = false)
    )
    private Set<Character> characters;

    @Column(name = "created")
    private LocalDateTime created;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Episode episode = (Episode) o;
        return sourceId == episode.sourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, name);
    }
}
