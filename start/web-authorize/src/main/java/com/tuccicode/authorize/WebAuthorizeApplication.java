package com.tuccicode.authorize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author tucci.lee
 */
@SpringBootApplication
@EnableCaching
public class WebAuthorizeApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebAuthorizeApplication.class, args);
    }
}
