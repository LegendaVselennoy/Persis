package com.example;

import com.example.model.User;
import com.example.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.Month;

@SpringBootApplication
public class PersisApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersisApplication.class, args);
	}

	@Bean
	public ApplicationRunner configure(UserRepository userRepository){
		return env->{
			User user1=new User("beth",LocalDate.of(2020, Month.AUGUST,3));

//			userRepository.save(user1);

			userRepository.findAll().forEach(System.out::println);
		};

	}

}
