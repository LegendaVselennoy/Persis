package com.example.ch4;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * Используя аннотацию @TestInstance(TestInstance.Lifecycle.PER_CLASS),
 * мы просим JUnit 5 создать один экземпляр тестового класса и повторно использовать его для всех тестовых методов.
 * Это позволит нам сделать аннотированные методы @BeforeAll и @AfterAll нестатическими и напрямую использовать в них
 * автоматически смонтированное поле экземпляра UserRepository
 */
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
abstract public class SprigDataJpaApplicationTests {

    @Autowired
    UserRepository userRepository;

    @BeforeAll
    void beforeAll() {
        userRepository.saveAll(generateUsers());
    }

    private static List<User> generateUsers() {
        List<User> users = new ArrayList<>();
        User john = new User("john", LocalDate.of(2020, Month.APRIL, 13));
        john.setEmail("john@somedomain.com");
        john.setLevel(1);
        john.setActive(true);
        //создать и настроить всего 1 пользователя
        users.add(john);
        //добавить в список всего 1 пользователя
        return users;
    }

    @AfterAll
    void afterAll() {
        userRepository.deleteAll();
    }

}
