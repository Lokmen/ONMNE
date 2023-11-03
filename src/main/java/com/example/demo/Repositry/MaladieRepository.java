package com.example.demo.Repositry;

import com.example.demo.Models.Maladie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaladieRepository extends JpaRepository<Maladie,Long> {
}
