package com.example.demo.ServiesIMP;


import com.example.demo.Models.UserModel;
import com.example.demo.Repositry.UserRepositry;
import com.example.demo.Servies.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserIMP implements UserServices {

@Autowired
    private UserRepositry userRepositry ;

    @Override
    public UserModel create(UserModel user) {
        return userRepositry.save(user);
    }

    @Override
    public UserModel update(UserModel user) {
        return userRepositry.save(user);
    }

    @Override
    public UserModel getOne(Long id) {
        return userRepositry.findById(id).orElse(null);
    }

    @Override
    public List<UserModel> getList() {
        return userRepositry.findAll();
    }



    public void delete(Long Id){
        userRepositry.deleteById(Id);
    }
}
