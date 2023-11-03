package com.example.demo.Servies;

import com.example.demo.Models.Maladie;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MaladieServices {
    public Maladie create (Maladie maladie);
    public Maladie update (Maladie maladie);
    public List<Maladie> FindAll ();
    public Maladie FindOne (Long Id);
    public void delete (Long Id);
}
