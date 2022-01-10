/**
 * Link the User entity and its custom requests to the database
 * @see User.java, UserRepository.java
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */

package com.amt.app.service;

import com.amt.app.entities.User;
import com.amt.app.repository.UserRepository;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class UserService {

    private UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User get(int id){
        return repository.findById(id).get();
    }

    public User getByUsername(String username){
        return repository.findByUsername(username);
    }

    public User addUser(User user){ return repository.save(user);}
}
