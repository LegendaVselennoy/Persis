package com.example.ch4;

import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryTest extends SprigDataJpaApplicationTests{

    @Test
    void testDeleteBulkByLevel() {
        int deleted = userRepository.deleteBulkByLevel(2);
        List<User> users = userRepository.findByLevel(2, Sort.by("username"));
        assertEquals(0, users.size());
    }

}
