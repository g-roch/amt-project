package com.example.amttest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class AmtTestApplicationTests {

    @Test
    void contextLoads() {
      assertEquals(7, 8);
    }

}
