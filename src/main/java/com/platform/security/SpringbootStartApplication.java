package com.platform.security;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import static org.springframework.boot.WebApplicationType.REACTIVE;

public class SpringbootStartApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SecurityApplication.class);
    }
    public static void main(String[] args) {
        new SpringApplicationBuilder(SecurityApplication.class).web(REACTIVE).run(args);
    }
}
