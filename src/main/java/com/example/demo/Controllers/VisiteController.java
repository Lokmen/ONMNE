package com.example.demo.Controllers;

import com.example.demo.Models.Maladie;
import com.example.demo.Models.UserModel;
import com.example.demo.Models.Visite;
import com.example.demo.Repositry.VisiteRepository;
import com.example.demo.ServiesIMP.MaladieIMP;
import com.example.demo.ServiesIMP.VisiteIMP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/Visite")
public class VisiteController {

    @Autowired
    VisiteRepository visiteRepository;
    @Autowired
    private  VisiteIMP visiteIMP;


    @PostMapping("/create")
    //format normal ==json donc je doit utiliser @RequestBody
    //exemple {"name":"lokmen", "email":"lokmen@gmail.com"}
    //en cas ou on va utiliser le type file data-form on ne met pas @RequestBody
    public Visite createvisite  (Visite visite){

        return visiteIMP.create(visite) ;
    }
    @PostMapping("/save")
    //format normal ==json donc je doit utiliser @RequestBody
    //exemple {"name":"lokmen", "email":"lokmen@gmail.com"}
    //en cas ou on va utiliser le type file data-form on ne met pas @RequestBody
    public Visite savevisite  (Visite visite){
       Maladie d = visite.getMaladieID();
        System.out.println("details:"+d.getId().toString() +"label: "+d.getLabel());

        //Maladie M = maladieIMP.FindOne(id);
       // System.out.println(M);
       // visite.setMaladieID(M);
        return visiteIMP.create(visite);
    }

    @GetMapping("/Liste")
    public List<Visite> visiteList  (){

        return visiteIMP.FindAll() ;
    }

    @PutMapping("/put/{id}")
    public Visite one(@PathVariable Long id, Visite visite) {
        Visite v  = visiteIMP.FindOne(id);
        if (v != null) {
            visite.setId(id);

            if(visite.getDateVisite()==null){
                visite.setDateVisite(v.getDateVisite());
            }

            return   visiteIMP.update(visite);
        } else {
            throw new RuntimeException("visite not found");
        }

    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> delete (@PathVariable Long id){
        Visite v  = visiteIMP.FindOne(id);
        HashMap<String,String> message = new HashMap();
        if (v != null) {
            visiteIMP.delete(id);
            message.put("visite","deleted");

        } else {
            message.put("visite","not founed");
        }
        return message;
    }
}
