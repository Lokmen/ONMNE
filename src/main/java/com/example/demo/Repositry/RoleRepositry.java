package com.example.demo.Repositry;

import com.example.demo.Models.ERole;
import com.example.demo.Models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepositry extends JpaRepository<Role,Long> {
    Optional<Role> findByName(ERole name);

}
