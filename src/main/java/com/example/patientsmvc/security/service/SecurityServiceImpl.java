package com.example.patientsmvc.security.service;

import com.example.patientsmvc.security.entities.AppRole;
import com.example.patientsmvc.security.entities.AppUser;
import com.example.patientsmvc.security.repositories.AppRoleRepository;
import com.example.patientsmvc.security.repositories.AppUserRepository;
import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository userRepo;
    private AppRoleRepository roleRepo;
    private PasswordEncoder p;

    @Override
    public AppUser saveNewUser(String username, String password, String passwordV) {
        if (!password.equals(passwordV)) throw new RuntimeException("Password not match !");
        String PasswordEncoded = p.encode(password);
        AppUser user = new AppUser();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(PasswordEncoded);
        user.setActive(true);
        AppUser savedAppUser = userRepo.save(user);
        return savedAppUser;

    }

    @Override
    public AppRole saveNewRole(String role, String description) {
        AppRole Role = roleRepo.findByRole(role);
        if (Role != null) throw new RuntimeException("Role Already existed");
        Role = new AppRole();
        Role.setRole(role);
        Role.setDescription(description);
        AppRole savedRole = roleRepo.save(Role);
        return savedRole;
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppRole Role = roleRepo.findByRole(role);

        if (Role == null) throw new RuntimeException("Role " + role + " Not Founde");
        AppUser User = userRepo.findByUsername(username);
        if (User == null) throw new RuntimeException("User " + username + " Not Found");
        User.getAppRoles().add(Role);

    }

    @Override
    public void removeRoleFromUser(String username, String role) {
        AppRole Role = roleRepo.findByRole(role);
        if (Role != null) throw new RuntimeException("Role " + role + " Not Founde");
        AppUser User = userRepo.findByUsername(username);
        if (User != null) throw new RuntimeException("User " + username + " Not Found");
        User.getAppRoles().remove(Role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        AppUser User = userRepo.findByUsername(username);
        return User;
    }
}
