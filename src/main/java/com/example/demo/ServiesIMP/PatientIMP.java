package com.example.demo.ServiesIMP;

import com.example.demo.Models.Patient;
import com.example.demo.Repositry.PatientRepositry;
import com.example.demo.Servies.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientIMP implements PatientServices {
    @Autowired
    private PatientRepositry patientRepositry;
    @Override
    public Patient create(Patient patient) {
        return patientRepositry.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        return patientRepositry.save(patient);
    }

    @Override
    public List<Patient> FindAll() {
        return patientRepositry.findAll();
    }

    @Override
    public Patient FindOne(Long Id) {
        return patientRepositry.findById(Id).orElse(null);
    }

    @Override
    public void delete(Long Id) {
    patientRepositry.deleteById(Id);
    }
}

