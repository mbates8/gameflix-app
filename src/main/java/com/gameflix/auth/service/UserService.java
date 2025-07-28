package com.gameflix.auth.service;

import com.gameflix.auth.model.User;
import com.gameflix.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;

    public UserService(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    public boolean register(String username, String rawPwd) {
        if (repo.findByUsername(username).isPresent()) return false;
        User u = new User();
        u.setUsername(username);
        u.setPasswordHash(encoder.encode(rawPwd));
        repo.save(u);
        return true;
    }

    public boolean login(String username, String rawPwd) {
        return repo.findByUsername(username)
                .map(u -> encoder.matches(rawPwd, u.getPasswordHash()))
                .orElse(false);
    }
}