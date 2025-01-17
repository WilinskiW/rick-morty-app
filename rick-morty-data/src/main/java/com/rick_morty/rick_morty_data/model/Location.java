package com.rick_morty.rick_morty_data.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "locations")
@Data
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "source_id", unique = true)
    private int sourceId;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "dimension")
    private String dimension;

    @Column(name = "created")
    private LocalDateTime created;

    @OneToMany(mappedBy = "origin", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Character> originCharacters;

    @OneToMany(mappedBy = "location", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Character> currentCharacters;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return sourceId == location.sourceId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, name);
    }
}
