package com.rick_morty.rick_morty_data.models.repository;

import com.rick_morty.rick_morty_data.model.User;
import com.rick_morty.rick_morty_data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveAndFindUserByUsername() {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("password123");

        userRepository.save(user);

        Optional<User> foundUser = userRepository.findUserByUsername("testUser");
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testUser");
    }
}
