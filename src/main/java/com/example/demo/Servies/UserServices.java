package com.example.demo.Servies;

import com.example.demo.Models.UserModel;

import java.util.List;

public interface UserServices {
    public UserModel create (UserModel user);
    public UserModel update (UserModel user);

    public UserModel getOne (Long id);

    public List<UserModel> getList();

    public void delete(Long Id);

}
