package com.example.demo.Controllers;

import com.example.demo.Models.ATCD;
import com.example.demo.Models.ERole;
import com.example.demo.Models.Patient;
import com.example.demo.Models.Role;
import com.example.demo.Repositry.ATCDRepository;
import com.example.demo.Repositry.PatientRepositry;
import com.example.demo.ServiesIMP.PatientIMP;
import com.example.demo.utils.StorageService;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

@RequestMapping("/Patient")
public class PatientController {

    @Autowired
    private PatientIMP patientIMP;
    @Autowired
    private StorageService storageService;
    @Autowired
    private PatientRepositry patientRepositry;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private ATCDRepository atcdRepository;

    @PostMapping("/create")
    public Patient create (Patient Patient, @RequestParam("file") MultipartFile file){

        String str = storageService.store(file);
        Patient.setPhoto(str);
       Set<ATCD> atcd= Patient.getPatientATCD();
       System.out.println(atcd.toString());

            return  patientIMP.create(Patient);

    }
    @PreAuthorize("hasRole('MEDECIN')")
    @GetMapping("/list")
    public List<Patient> liste(){
        return patientIMP.FindAll();

    }

    @PostMapping("/save")
    public ResponseEntity<?> save (Patient patient, @RequestParam("file") MultipartFile file) throws  Exception {

        String str = storageService.store(file);
        System.out.println(patient.getDatenes());
        patient.setPhoto(str);

        String from ="itService.mail.fr";
        String to= patient.getEmail();
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper messaHelper= new MimeMessageHelper(message);
        messaHelper.setSubject("confirm registration");
        messaHelper.setFrom(from);
        messaHelper.setTo(to);
        messaHelper.setText("<html><body><p> bonjour docteur :</p>" +
                "<br>"+ patient.getUsername() +" <br>" +
                "<a href='http://localhost:8085/Patient/confirm?email="+patient.getEmail()+"'>verifier</a></body></html>",true);

        javaMailSender.send(message);
        patientIMP.create(patient);
        return ResponseEntity.ok().body("Patient registered successfully!,check your email to confirm");

        //return ResponseEntity.ok(new MessageResponse("Medecin registered successfully!,check your email to confirm"));
    }
    @GetMapping("/confirm")
    public ResponseEntity<?> confirm (@RequestParam String email){
        Patient patient = patientRepositry.findFirstByEmail(email);
        //Medecin medecin= medecinRepositry.findFirstByEmail(email);
        if(patient !=null) {
            patient.setConfirm(true);
            patientRepositry .save(patient);
            return ResponseEntity.ok().body("Patient is confirmed");
        }
        return ResponseEntity.ok().body("Patient is not confirmed");

    }

    @GetMapping("/one/{id}")
    public Patient one(@PathVariable Long id){
        return patientIMP.FindOne(id);

    }
    @PutMapping("/put/{id}")
    public Patient one(@PathVariable Long id, Patient Patient) {
        Patient findPatient = patientIMP.FindOne(id);

        if (findPatient != null) {

            Patient.setId(id);
            return   patientIMP.update(Patient);
        } else {
            throw new RuntimeException("Patient not found");
        }

    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> delete (@PathVariable Long id){
        Patient findPatient = patientIMP.FindOne(id);
        HashMap<String,String> message = new HashMap();
        if (findPatient != null) {
            patientIMP.delete(id);
            message.put("user","deleted");

        } else {
            message.put("user","not founed");
        }
        return message;
    }

}
