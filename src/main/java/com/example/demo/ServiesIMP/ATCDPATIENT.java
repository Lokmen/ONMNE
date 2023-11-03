package com.example.demo.ServiesIMP;

import com.example.demo.Models.Visite;
import com.example.demo.Repositry.ATCDRepository;
import com.example.demo.Repositry.PatientRepositry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ATCDPATIENT {
  @Autowired
   private ATCDRepository atcdRepository;
  @Autowired
  private PatientRepositry patientRepositry;


}
