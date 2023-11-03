package com.example.demo.Repositry;

import com.example.demo.Models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepositry extends JpaRepository<Patient,Long> {
    Patient findFirstByEmail(String email);
}
