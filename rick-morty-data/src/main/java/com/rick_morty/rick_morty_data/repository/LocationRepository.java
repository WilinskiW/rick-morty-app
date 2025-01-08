package com.rick_morty.rick_morty_data.repository;

import com.rick_morty.rick_morty_data.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Integer> {
}
