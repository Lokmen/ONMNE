package com.example.demo.Controllers;

import com.example.demo.Models.Maladie;
import com.example.demo.Models.Visite;
import com.example.demo.Repositry.MaladieRepository;
import com.example.demo.ServiesIMP.MaladieIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Maladie")
public class MaladieController {

    @Autowired
    private MaladieRepository maladieRepository;
    @Autowired
    private MaladieIMP maladieIMP;
    @PostMapping("/create")
        public Maladie createMaladie (Maladie maladie){

        return maladieIMP.create(maladie) ;
    }
    @GetMapping("/Liste")
    public List<Maladie> maladieList  (){

        return maladieIMP.FindAll() ;
    }


    @PutMapping("/put/{id}")
    public Maladie one(@PathVariable Long id, Maladie maladie ) {
      Maladie m = maladieIMP.FindOne(id);
        if (m != null) {
            maladie.setId(id);



            return   maladieIMP.update(maladie);
        } else {
            throw new RuntimeException("maladie not found");
        }

    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> delete (@PathVariable Long id){
        Maladie m = maladieIMP.FindOne(id);
        HashMap<String,String> message = new HashMap();
        if (m != null) {
            maladieIMP.delete(id);
            message.put("maladie","deleted");

        } else {
            message.put("maladie","not founed");
        }
        return message;
    }

}
