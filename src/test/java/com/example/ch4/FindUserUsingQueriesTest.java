package com.example.ch4;

import com.example.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindUserUsingQueriesTest extends SprigDataJpaApplicationTests{

    @Test
    void testFindAll() {
        List<User> users = userRepository.findAll();
        assertEquals(1, users.size());
    }

    @Test
    void testFindUser() {
        User beth = userRepository.findByUsername("john");
        assertEquals("john", beth.getUsername());
    }

    @Test
    void testFindAllByOrderByUsernameAsc() {
        List<User> users = userRepository.findAllByOrderByUsernameAsc();
        assertAll(
                  () -> assertEquals(1, users.size()));
//               ,() -> assertEquals("beth", users.get(0).getUsername()),
//                () -> assertEquals("stephanie",
//                        users.get(users.size() - 1).getUsername()));
    }

    @Test
    void testFindByRegistrationDateBetween() {
        List<User> users = userRepository.findByRegistrationDateBetween(
                LocalDate.of(2020, Month.JULY, 1),
                LocalDate.of(2020, Month.DECEMBER, 31));
        assertEquals(0, users.size());
    }
}
