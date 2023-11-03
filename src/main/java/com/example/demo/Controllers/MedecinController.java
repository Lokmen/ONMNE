package com.example.demo.Controllers;

import com.example.demo.Models.*;

import com.example.demo.Payload.request.LoginRequest;
import com.example.demo.Payload.request.SignupRequest;
import com.example.demo.Payload.response.JwtResponse;
import com.example.demo.Payload.response.MessageResponse;
import com.example.demo.Repositry.MedecinRepositry;
import com.example.demo.Repositry.RoleRepositry;
import com.example.demo.ServiesIMP.MedecinIMP;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.RefreshTokenService;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.utils.StorageService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.mail.javamail.JavaMailSender;
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)


@RequestMapping("/Medecin")
public class MedecinController {
@Autowired
private JavaMailSender javaMailSender;
    @Autowired
    private MedecinIMP medecinIMP;
    @Autowired
private MedecinRepositry medecinRepositry;
    @Autowired
    private StorageService storageService;
    @Autowired
    private RoleRepositry roleRepositry;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;
    @PostMapping("/create")
    public Medecin create (Medecin medecin,@RequestParam("file") MultipartFile file){

        String str = storageService.store(file);
        System.out.println(medecin.getDatenes());
        medecin.setPhoto(str);
        return medecinIMP.create(medecin);
    }
    @PostMapping("/save")
    public ResponseEntity<?> save (Medecin medecin, @RequestParam("file") MultipartFile file) throws  Exception {

        String str = storageService.store(file);
        System.out.println(medecin.getDatenes());
        medecin.setPhoto(str);

        String from ="itService.mail.fr";
        String to= medecin.getEmail();
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper messaHelper= new MimeMessageHelper(message);
        messaHelper.setSubject("confirm registration");
        messaHelper.setFrom(from);
        messaHelper.setTo(to);
        messaHelper.setText("<html><body><p> bonjour docteur :</p>" +
                "<br>"+ medecin.getUsername() +" <br>" +
                "<a href='http://localhost:8085/Medecin/confirm?email="+medecin.getEmail()+"'>verifier</a></body></html>",true);

        javaMailSender.send(message);
    medecinIMP.create(medecin);
        return ResponseEntity.ok().body("Medecin registered successfully!,check your email to confirm");

        //return ResponseEntity.ok(new MessageResponse("Medecin registered successfully!,check your email to confirm"));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid SignupRequest signUpRequest, @RequestParam("file") MultipartFile file)throws MessagingException {
        if (medecinRepositry.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (medecinRepositry.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new medecin's account
        Medecin medecin=new Medecin(null,signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),null,
                signUpRequest.getDatenes(),null,null,null,null);
        String str = storageService.store(file);
        // System.out.println(user.getDatenes());
        medecin.setPhoto(str);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role medecinRole = roleRepositry.findByName(ERole.ROLE_MEDECIN)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(medecinRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "USER":
                        Role USER = roleRepositry.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(USER);

                        break;
                    case "PATIENT":
                        Role PATIENT = roleRepositry.findByName(ERole.ROLE_PATIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(PATIENT);

                        break;
                    default:
                        Role MEDECIN = roleRepositry.findByName(ERole.ROLE_MEDECIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(MEDECIN);
                }
            });
        }

        medecin.setRoles(roles);
        medecinRepositry.save(medecin);
        String from ="itService.mail.fr";
        String to= medecin.getEmail();
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper messaHelper= new MimeMessageHelper(message);
        messaHelper.setSubject("confirm registration");
        messaHelper.setFrom(from);
        messaHelper.setTo(to);
        messaHelper.setText("<html><body><p> bonjour  :</p>" +
                "<br>"+ medecin.getUsername() +" <br>" +
                "<a href='http://localhost:8085/Medecin/confirm?email="+medecin.getEmail()+"'>verifier</a></body></html>",true);

        javaMailSender.send(message);


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }



    @GetMapping("/confirm")
    public ResponseEntity<?> confirm (@RequestParam String email){
        Medecin medecin= medecinRepositry.findFirstByEmail(email);
        if(medecin !=null) {
            medecin.setConfirm(true);
           medecinRepositry .save(medecin);
            return ResponseEntity.ok().body("user is confirmed");
        }
        return ResponseEntity.ok().body("user is not confirmed");

    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateMedecin(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        Optional<Medecin> medecin = medecinRepositry.findByUsername(loginRequest.getUsername());
        if (medecin.get().isConfirm()==true) {
            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

            String jwt = jwtUtils.generateJwtToken(userDetails);

            List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
                    .collect(Collectors.toList());

            RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

            return ResponseEntity.ok(new JwtResponse(jwt, refreshToken.getToken(), userDetails.getId(),
                    userDetails.getUsername(), userDetails.getEmail(), roles));

        }else {
            throw new RuntimeException("user not confirmed");
        }

    }



    @PostMapping("/signout")
    public ResponseEntity<?> signout (){

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();
        refreshTokenService.deleteByUserId(userId);
        return ResponseEntity.ok().body("");

    }

    @GetMapping("/list")
    public List<Medecin> liste(){
        return medecinIMP.FindAll();

    }

    @GetMapping("/one/{id}")
    public Medecin one(@PathVariable Long id){
        return medecinIMP.FindOne(id);

    }
    @PutMapping("/put/{id}")
    public Medecin one(@PathVariable Long id, Medecin medecin) {
        Medecin findMedecin = medecinIMP.FindOne(id);

        if (findMedecin != null) {

            medecin.setId(id);
            return   medecinIMP.update(medecin);
        } else {
            throw new RuntimeException("medecin not found");
        }

    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> delete (@PathVariable Long id){
        Medecin findMedecin = medecinIMP.FindOne(id);
        HashMap<String,String> message = new HashMap();
        if (findMedecin != null) {
            medecinIMP.delete(id);
            message.put("user","deleted");

        } else {
            message.put("user","not founed");
        }
        return message;
    }

}
