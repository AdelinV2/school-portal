package com.school.school_portal.repository;

import com.school.school_portal.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update User u set u.password = :password where u.email = :email")
    void updatePasswordByEmail(@Param("email") String email, @Param("password") String encode);

    @Query("select u from User u join Student s on u.id = s.user.id where s.id = :studentId")
    User findByStudentId(@Param("studentId") Integer studentId);
}