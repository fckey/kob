package com.kob.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class BackendApplicationTests {

    @Test
    void contextLoads() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode("fckey"));
        System.out.println(encoder.encode("fckey"));
        System.out.println(encoder.encode("fckey"));
        System.out.println(encoder.encode("fckey"));
        System.out.println(encoder.matches("fckey", "$2a$10$o.UeaOwW/TkDYag3x35Z/.bz/WWg1ZaYdCwpCImoCpZzwdUiL9gfi"));

    }

}
