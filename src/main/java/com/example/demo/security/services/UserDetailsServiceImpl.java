package com.example.demo.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import com.securityEcommerce.models.User;
//import com.securityEcommerce.repository.UserRepository;
import com.example.demo.Models.UserModel;
import com.example.demo.Repositry.UserRepositry;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 @Autowired
UserRepositry userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    UserModel user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

    //return UserDetailsImpl.build(user);
      return UserDetailsImpl.build(user);
  }

}
