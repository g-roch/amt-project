package com.amt.app.service;

import com.amt.app.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CartService {

    @Autowired
    private CartRepository repository;
}
