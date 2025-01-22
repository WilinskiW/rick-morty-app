package com.rick_morty.rick_morty_data.models.repository;

import com.rick_morty.rick_morty_data.model.Location;
import com.rick_morty.rick_morty_data.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class LocationRepositoryTest {
    @Autowired
    private LocationRepository locationRepository;

    @Test
    void shouldSaveAndFindLocationById() {
        Location location = new Location();
        location.setSourceId(1);
        location.setName("Earth");
        location.setType("Planet");
        location.setDimension("Dimension C-137");

        locationRepository.save(location);

        Optional<Location> found = locationRepository.findById(location.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Earth");
    }

    @Test
    void shouldCheckIfLocationExistsBySourceId() {
        Location location = new Location();
        location.setSourceId(2);
        location.setName("Abadango");
        locationRepository.save(location);

        boolean exists = locationRepository.existsBySourceId(2);
        assertThat(exists).isTrue();
    }
}
