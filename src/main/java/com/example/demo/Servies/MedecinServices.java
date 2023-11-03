package com.example.demo.Servies;

import com.example.demo.Models.Medecin;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MedecinServices {
    public Medecin create (Medecin medecin);
    public Medecin update (Medecin medecin);
    public List<Medecin> FindAll ();
    public Medecin FindOne (Long Id);
    public void delete (Long Id);
}
