package com.gameflix.auth.service;

import com.gameflix.auth.repository.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService svc;

    @Autowired
    private UserRepository repo;

    @BeforeEach
    void cleanAndSeed() {
        repo.deleteAll();
        // Seed a known user using the service so the hash is valid
        svc.register("existing", "Existing123!");
    }

    @Test
    void registerNewUser_ShouldSucceed() {
        boolean ok = svc.register("newuser", "Password123!");
        Assertions.assertTrue(ok, "Register should return true for a fresh username");
        Assertions.assertTrue(repo.findByUsername("newuser").isPresent());
    }

    @Test
    void registerDuplicate_ShouldFail() {
        boolean ok = svc.register("existing", "doesntmatter");
        Assertions.assertFalse(ok, "Register should return false for duplicate username");
    }

    @Test
    void loginValidCredentials_ShouldSucceed() {
        // Use the seeded user with known password
        Assertions.assertTrue(svc.login("existing", "Existing123!"),
                "Login must succeed with correct creds");
    }

    @Test
    void loginInvalidPassword_ShouldFail() {
        svc.register("foo","Bar123!");
        Assertions.assertFalse(svc.login("foo","WrongPass"),
                "Login must fail with bad password");
    }
}