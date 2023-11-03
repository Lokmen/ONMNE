package com.example.demo.Servies;

import com.example.demo.Models.Medecin;
import com.example.demo.Models.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PatientServices {

    public Patient create (Patient patient);
    public Patient update (Patient patient);
    public List<Patient> FindAll ();
    public Patient FindOne (Long Id);
    public void delete (Long Id);
}
