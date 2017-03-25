package com.wula.housedata;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by lishaohua on 16-7-16.
 */

@EnableAutoConfiguration
@ComponentScan()
@SpringBootApplication
public class ApplicationConfig {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ApplicationConfig.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
    }
}
