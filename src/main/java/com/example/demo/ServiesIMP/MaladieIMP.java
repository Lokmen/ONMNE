package com.example.demo.ServiesIMP;

import com.example.demo.Models.Maladie;
import com.example.demo.Repositry.MaladieRepository;
import com.example.demo.Repositry.MedecinRepositry;
import com.example.demo.Servies.MaladieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MaladieIMP implements MaladieServices {

    @Autowired
    private MaladieRepository maladieRepository;

    @Override
    public Maladie create(Maladie maladie) {
        return maladieRepository.save(maladie);
    }

    @Override
    public Maladie update(Maladie maladie) {
        return maladieRepository.save(maladie);
    }

    @Override
    public List<Maladie> FindAll() {
        return maladieRepository.findAll();
    }

    @Override
    public Maladie FindOne(Long Id) {
        return maladieRepository.findById(Id).orElse(null);
    }

    @Override
    public void delete(Long Id) {
    maladieRepository.deleteById(Id);
    }


}
