package com.amt.app.service;

import com.amt.app.entities.User;
import com.amt.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository repository;

    @Autowired
    public void setUserRepository(UserRepository userRepository){
        this.repository = userRepository;
    }

    public User get(int id){
        return repository.findById(id).get();
    }

    public User getByUsername(String username){
        return repository.findByUsername(username);
    }
}
