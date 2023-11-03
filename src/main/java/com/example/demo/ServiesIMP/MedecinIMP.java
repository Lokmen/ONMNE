package com.example.demo.ServiesIMP;

import com.example.demo.Models.Medecin;
import com.example.demo.Models.UserModel;
import com.example.demo.Repositry.MedecinRepositry;
import com.example.demo.Servies.MedecinServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedecinIMP implements MedecinServices {

    @Autowired
    private MedecinRepositry medecinRepositry ;



    @Override
    public Medecin create(Medecin medecin) {
        return medecinRepositry.save(medecin);    }

    @Override
    public Medecin update(Medecin medecin) {
        return medecinRepositry.save(medecin);    }


    @Override
    public List<Medecin> FindAll() {
    return medecinRepositry.findAll();
    }

    @Override
    public Medecin FindOne(Long Id) {
return medecinRepositry.findById(Id).orElse(null) ;   }

    public void delete(Long Id){
        medecinRepositry.deleteById(Id);
    }
}

