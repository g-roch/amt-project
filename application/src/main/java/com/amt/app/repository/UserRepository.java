/**
 * Manage requests to the database linked to users
 * @see User.java, UserService.java
 * @author Dylan Canton, Lucas Gianinetti, Nicolas Hungerbühler, Gabriel Roch, Christian Zaccaria
 */


package com.amt.app.repository;

import com.amt.app.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    /**
     * Get a user by is name
     * @param username name of user
     * @return User
     */
    User findByUsername(String username);
}
