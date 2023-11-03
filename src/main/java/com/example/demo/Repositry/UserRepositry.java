package com.example.demo.Repositry;

import com.example.demo.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositry extends JpaRepository<UserModel,Long> {


    Optional<UserModel> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    UserModel findFirstByEmail (String email);
    UserModel findByPasswordResetToken(String passwordResetToken);
}