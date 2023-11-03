package com.example.demo.Controllers;


import com.example.demo.Models.ERole;
import com.example.demo.Models.Role;
import com.example.demo.Models.UserModel;

import com.example.demo.Payload.request.SignupRequest;
import com.example.demo.Payload.response.MessageResponse;
import com.example.demo.Repositry.RoleRepositry;
import com.example.demo.Repositry.UserRepositry;
import com.example.demo.ServiesIMP.UserIMP;
import com.example.demo.utils.StorageService;
import jakarta.mail.MessagingException;
import jakarta.servlet.annotation.HandlesTypes;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

@RequestMapping("/User")
public class UserController{
    @Autowired
    private UserIMP userIMP;
    @Autowired
private StorageService storageService;
    @Autowired
    private UserRepositry userRepositry;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private RoleRepositry roleRepository;

    @PostMapping("/create")
    //format normal ==json donc je doit utiliser @RequestBody
    //exemple {"name":"lokmen", "email":"lokmen@gmail.com"}
    //en cas ou on va utiliser le type file data-form on ne met pas @RequestBody
    public UserModel createUser (UserModel user,@RequestParam("file") MultipartFile file){

        String str = storageService.store(file);
  System.out.println(user.getDatenes());
        user.setPhoto(str);
        System.out.println(user.getDatenes().toString());
        return userIMP.create(user)   ;
    }

    @PostMapping("/signup")
    //format normal ==json donc je doit utiliser @RequestBody
    //exemple {"name":"lokmen", "email":"lokmen@gmail.com"}
    //en cas ou on va utiliser le type file data-form on ne met pas @RequestBody
    public ResponseEntity<?> signup (@Valid SignupRequest signUpRequest, @RequestParam("file") MultipartFile file) throws MessagingException {

        if (userRepositry.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }
        if (userRepositry.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }
        //create an object user from user construxtor
        UserModel user=new UserModel(null,signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),null,
                signUpRequest.getDatenes());
        String str = storageService.store(file);
       // System.out.println(user.getDatenes());
        user.setPhoto(str);
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        Role modRole = roleRepository.findByName(ERole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(modRole);
        user.setRoles(roles);

        userRepositry.save(user);

        //System.out.println(user.getDatenes().toString());

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
       @GetMapping("/confirm")
    public ResponseEntity<?> confirm (@RequestParam String email){
        UserModel user= userRepositry.findFirstByEmail(email);
        if(user !=null) {
            user.setConfirm(true);
            userRepositry.save(user);
            return ResponseEntity.ok().body("user is confirmed");
        }
        return ResponseEntity.ok().body("user is not confirmed");

    }

@GetMapping("/list")
    public List<UserModel> liste(){
        return userIMP.getList();

}

@GetMapping("/one/{id}")
    public UserModel one(@PathVariable Long id){
        return userIMP.getOne(id);

}
    @PutMapping("/put/{id}")
    public UserModel one(@PathVariable Long id, UserModel user) {
        UserModel findUser = userIMP.getOne(id);

        if (findUser != null) {

            user.setId(id);
          return   userIMP.update(user);
        } else {
            throw new RuntimeException("user not found");
        }

    }

    @DeleteMapping("/delete/{id}")
    public HashMap<String,String> delete (@PathVariable Long id){
        UserModel findUser = userIMP.getOne(id);
        HashMap<String,String> message = new HashMap();
        if (findUser != null) {
            userIMP.delete(id);
        message.put("user","deleted");

        } else {
            message.put("user","not founed");
        }
return message;
    }}