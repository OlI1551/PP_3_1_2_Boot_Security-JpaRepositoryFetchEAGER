package ru.kata.spring.boot_security.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.util.HashSet;
import java.util.Set;


@Service
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public boolean registerUser(User user, boolean roleAdmin) {
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB != null) {
            return false;
        }
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, "ROLE_USER"));
        if (!userRepository.existsById(1L) || roleAdmin) {
            roles.add(new Role(1L, "ROLE_ADMIN"));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Transactional
    public boolean saveUser(User user, boolean roleAdmin) {
        User userFromDB = userRepository.getById(user.getId());
        userFromDB.setFirstName(user.getFirstName());
        userFromDB.setLastName(user.getLastName());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setUsername(user.getUsername());
        userFromDB.setPassword(user.getPassword());
        userFromDB.setPasswordConfirm(user.getPasswordConfirm());
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(2L, "ROLE_USER"));
        if (!userRepository.existsById(1L) || roleAdmin) {
            roles.add(new Role(1L, "ROLE_ADMIN"));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }
}
