package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.models.Role;

@Component
public class RoleDataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleDataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        roleRepository.save(new Role(1L, "ROLE_ADMIN"));
        roleRepository.save(new Role(2L, "ROLE_USER"));
        // Добавляем еще роли при необходимости
    }
}
