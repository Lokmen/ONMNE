package com.example.demo.Controllers;

import com.example.demo.Models.ERole;
import com.example.demo.Models.RefreshToken;
import com.example.demo.Models.Role;
import com.example.demo.Models.UserModel;
import com.example.demo.Payload.request.LoginRequest;
import com.example.demo.Payload.request.SignupRequest;
import com.example.demo.Payload.response.JwtResponse;
import com.example.demo.Payload.response.MessageResponse;
import com.example.demo.Repositry.RoleRepositry;
import com.example.demo.security.jwt.JwtUtils;
import com.example.demo.security.services.RefreshTokenService;
import com.example.demo.security.services.UserDetailsImpl;
import com.example.demo.utils.StorageService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Repositry.UserRepositry;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    @Autowired
    UserRepositry userRepository;

    @Autowired
    RoleRepositry roleRepository;

    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RefreshTokenService refreshTokenService;

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private StorageService storageService;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid SignupRequest signUpRequest,@RequestParam("file") MultipartFile file)throws MessagingException {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserModel user=new UserModel(null,signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()),null,
                signUpRequest.getDatenes());
        String str = storageService.store(file);
        // System.out.println(user.getDatenes());
        user.setPhoto(str);

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "MEDECIN":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_MEDECIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "PATIEN":
                        Role modRole = roleRepository.findByName(ERole.ROLE_PATIENT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);
        String from ="itService.mail.fr";
        String to= user.getEmail();
        MimeMessage message=javaMailSender.createMimeMessage();
        MimeMessageHelper messaHelper= new MimeMessageHelper(message);
        messaHelper.setSubject("confirm registration");
        messaHelper.setFrom(from);
        messaHelper.setTo(to);
        messaHelper.setText("<html><body><p> bonjour  :</p>" +
                "<br>"+ user.getUsername() +" <br>" +
                "<a href='http://localhost:8085/api/auth/confirm?email="+user.getEmail()+"'>verifier</a></body></html>",true);

        javaMailSender.send(message);


        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    @GetMapping("/confirm")
    public ResponseEntity<?> confirm (@RequestParam String email){
        UserModel user= userRepository.findFirstByEmail(email);
        if(user !=null) {
            user.setConfirm(true);
            userRepository.save(user);
            return ResponseEntity.ok().body("user is confirmed");
        }
        return ResponseEntity.ok().body("user is not confirmed");

    }


    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        Optional<UserModel> u = userRepository.findByUsername(loginRequest.getUsername());
        if (u.get().isConfirm()==true) {
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


}
