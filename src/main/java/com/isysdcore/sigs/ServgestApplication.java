package com.isysdcore.sigs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.isysdcore.sigs"})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ServgestApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(ServgestApplication.class, args);
    }

}
