package com.example.repository;

import com.example.model.Projection;
import com.example.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.util.Streamable;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    List<User> findAllByOrderByUsernameAsc();

    List<User> findByRegistrationDateBetween(LocalDate start, LocalDate end);

    List<User> findByUsernameAndEmail(String username, String email);

    List<User> findByUsernameOrEmail(String username, String email);

    List<User> findByUsernameIgnoreCase(String username);

    List<User> findByLevelOrderByUsernameDesc(int level);

    List<User> findByLevelGreaterThanEqual(int level);

    List<User> findByUsernameContaining(String text);

    List<User> findByUsernameLike(String text);

    List<User> findByUsernameStartingWith(String start);

    List<User> findByUsernameEndingWith(String end);

    List<User> findByActive(boolean active);

    List<User> findByRegistrationDateIn(Collection<LocalDate> dates);

    List<User> findByRegistrationDateNotIn(Collection<LocalDate> dates);

    User findFirstByOrderByUsernameAsc();

    User findTopByOrderByRegistrationDateDesc();

    Page<User> findAll(Pageable pageable);

    List<User> findFirst2ByLevel(int level, Sort sort);

    List<User> findByLevel(int level, Sort sort);

    List<User> findByActive(boolean active, Pageable pageable);

    Streamable<User> findByEmailContaining(String text);

    Streamable<User> findByLevel(int level);

    // Метод findNumberOfUsersByActivity вернет количество активных пользователей
    @Query("select count(u) from User u where u.active = ?1")
    int findNumberOfUsersByActivity(boolean active);

    // Метод findByLevelAndActive вернет пользователей с уровнем и активным статусом, указанными в качестве именованных параметров.
    // Аннотация @Param будет сопоставлять параметр :level запроса с аргументом level метода,
    // а параметр :active запроса с активным аргументом метода. Это особенно полезно,
    // когда вы изменяете порядок параметров из сигнатуры метода, а запрос не обновляется.
    @Query("select u from User u where u.level = :level and u.active = :active")
    List<User> findByLevelAndActive(@Param("level") int level, @Param("active") boolean active);

    /**
     * Метод findNumberOfUsersByActivityNative вернет количество пользователей с заданным активным статусом.
     * Установка флага nativeQuery в true указывает на то, что, в отличие от предыдущих запросов, написанных с помощью JPQL,
     * этот запрос написан с использованием машинного SQL, специфичного для базы данных
     */
    @Query(value = "SELECT COUNT(*) FROM USERS WHERE ACTIVE = ?1", nativeQuery = true)
    int findNumberOfUsersByActivityNative(boolean active);

    /**
    * Метод findByAsArrayAndSort вернет список массивов, каждый из которых содержит имя пользователя и длину сообщения электронной почты,
    * после фильтрации по имени пользователя. Второй параметр Sort позволит упорядочить результат запроса на основе разных критериев
     */
    @Query("select u.username, LENGTH(u.email) as email_length from #{#entityName} u where u.username like %?1%")
    List<Object[]> findByAsArrayAndSort(String text, Sort sort);

    List<Projection.UserSummary> findByRegistrationDateAfter(LocalDate date);

    List<Projection.UsernameOnly> findByEmail(String username);

    <T> List<T> findByEmail(String username, Class<T> type);

    @Modifying
    @Transactional
    @Query("update User u set u.level = ?2 where u.level = ?1")
    int updateLevel(int oldLevel, int newLevel);

    @Transactional
    int deleteByLevel(int level);

    @Transactional
    @Modifying
    @Query("delete from User u where u.level = ?1")
    int deleteBulkByLevel(int level);
}
