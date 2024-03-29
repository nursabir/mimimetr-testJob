package com.example.mimimetrtestjob.service;


import com.example.mimimetrtestjob.model.User;
import com.example.mimimetrtestjob.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void save(String email){
        userRepository.save(User.builder().email(email).build());
    }


}


