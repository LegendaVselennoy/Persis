package com.example.model;

import org.springframework.beans.factory.annotation.Value;

public class Projection {

    public interface UserSummary{

        // Метод getUsername вернет поле username.
        String getUsername();

     // Метод getInfo аннотируется аннотацией @Value и возвращает конкатенацию поля имени пользователя,
     // пробела и поля электронной почты
        @Value("#{target.username} #{target.email}")
        String getInfo();
    }

    public static class UsernameOnly{
        private String username;

        public UsernameOnly(String username) {
            this.username = username;
        }

        public String getUsername() {
            return username;
        }
    }
}
