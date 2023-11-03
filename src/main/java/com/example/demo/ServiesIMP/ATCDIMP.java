package com.example.demo.ServiesIMP;

import com.example.demo.Models.ATCD;
import com.example.demo.Repositry.ATCDRepository;
import com.example.demo.Servies.ATCDServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ATCDIMP implements ATCDServices {
   @Autowired
    ATCDRepository atcdRepository;
    @Override
    public ATCD create(ATCD atcd) {
        return atcdRepository.save(atcd);
    }

    @Override
    public ATCD update(ATCD atcd) {
        return null;
    }

    @Override
    public List<ATCD> FindAll() {
        return atcdRepository.findAll();
    }

    @Override
    public ATCD FindOne(Long Id) {
        return atcdRepository.findById(Id).orElse(null);
    }

    @Override
    public void delete(Long Id) {
        atcdRepository.deleteById(Id);
    }
}
