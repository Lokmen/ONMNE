package com.example.demo.Repositry;

import com.example.demo.Models.Medecin;
import com.example.demo.Models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedecinRepositry extends JpaRepository<Medecin,Long> {
    Optional<Medecin> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    Medecin findFirstByEmail (String email);
    Medecin findByPasswordResetToken(String passwordResetToken);
}
