package com.rick_morty.rick_morty_data.repository.web;

import com.rick_morty.rick_morty_data.model.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer> {
    boolean existsBySourceId(Integer sourceId);

    @Query("select x from Character x where x.name like (:pattern)")
    List<Character> findLike(String pattern);

    boolean existsByName(String name);

    @Query(value = "SELECT * FROM characters WHERE id NOT IN (" +
            "SELECT character_id FROM character_episode WHERE episode_id = (:episodeId))",
            nativeQuery = true)
    List<Character> findByEpisodeNotIn(int episodeId);

    @Query(value = "SELECT * FROM characters WHERE location_id <> :locationId or location_id is null",
            nativeQuery = true)
    List<Character> findByLocationNotIn(@Param("locationId") int locationId);
}
