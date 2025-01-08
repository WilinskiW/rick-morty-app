package com.rick_morty.rick_morty_data.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_favorites")
@Data
public class UserFavorites {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username")
    private String username;

    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;
}
