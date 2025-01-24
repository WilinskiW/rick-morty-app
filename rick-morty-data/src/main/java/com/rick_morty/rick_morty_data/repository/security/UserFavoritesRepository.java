package com.rick_morty.rick_morty_data.repository.security;

import com.rick_morty.rick_morty_data.model.UserFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFavoritesRepository extends JpaRepository<UserFavorites, Integer> {
}
