package com.example.demo.ServiesIMP;


import com.example.demo.Models.Visite;
import com.example.demo.Repositry.VisiteRepository;
import com.example.demo.Servies.VisiteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisiteIMP implements VisiteServices {

    @Autowired
    private VisiteRepository visiteRepository;


    @Override
    public Visite create(Visite visite) {
        return visiteRepository.save(visite);
    }

    @Override
    public Visite update(Visite visite) {
        return visiteRepository.save(visite);
    }

    @Override
    public List<Visite> FindAll() {
        return visiteRepository.findAll();
    }

    @Override
    public Visite FindOne(Long Id) {
        return visiteRepository.findById(Id).orElse(null);
    }

    @Override
    public void delete(Long Id) {
        visiteRepository.deleteById(Id);

    }
}
