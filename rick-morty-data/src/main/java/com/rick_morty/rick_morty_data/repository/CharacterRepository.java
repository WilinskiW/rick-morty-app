package com.rick_morty.rick_morty_data.repository;

import com.rick_morty.rick_morty_data.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    @Query("select x from Character x where x.source_id in (:sourceIds)")
    List<Character> withSourceIds(List<Long> sourceIds);
}
