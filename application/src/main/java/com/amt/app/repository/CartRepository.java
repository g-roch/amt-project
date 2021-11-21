package com.amt.app.repository;

import com.amt.app.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CartRepository extends JpaRepository<Cart, Integer> {

}
