package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/read-id").setViewName("admin/readId");
        registry.addViewController("/limit").setViewName("admin/limit");
        registry.addViewController("/edit-id").setViewName("admin/editId");
        registry.addViewController("/delete-id").setViewName("admin/deleteId");

    }
}
