package com.example.demo.Servies;

import com.example.demo.Models.Visite;
import org.springframework.stereotype.Service;

import java.util.List;


public interface VisiteServices {
    public Visite create (Visite visite );
    public Visite update (Visite visite);
    public List<Visite> FindAll ();
    public Visite FindOne (Long Id);
    public void delete (Long Id);
}
