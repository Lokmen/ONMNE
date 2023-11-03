package com.example.demo.Controllers;

import com.example.demo.Models.ATCD;
import com.example.demo.Models.Visite;
import com.example.demo.Repositry.ATCDRepository;
import com.example.demo.Servies.ATCDServices;
import com.example.demo.ServiesIMP.ATCDIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/ATCD")
public class ATCDController {
    @Autowired
    ATCDRepository atcdRepository;
    @Autowired
    ATCDIMP atcdimp;


    @GetMapping("/Liste")
    public List<ATCD> atcdList  (){

        return atcdimp.FindAll() ;
    }

}
