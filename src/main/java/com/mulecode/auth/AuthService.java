package com.mulecode.auth;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mulecode.dao.UserDao;
import com.mulecode.dao.UserDaoImpl;
import com.mulecode.model.User;

import java.util.Optional;

public class AuthService {

    private final UserDao userDao;
    private User loggedInUser = null;

    public AuthService() {
        this.userDao = new UserDaoImpl();
    }

    public boolean register(String username, String email, String password) {
        // Check if username already exists
        Optional<User> existing = userDao.findByUsername(username);
        if (existing.isPresent()) {
            System.out.println("Username already taken: " + username);
            return false;
        }

        // Hash the password with BCrypt
        String passwordHash = BCrypt.withDefaults().hashToString(12, password.toCharArray());

        // Save the new user
        User user = new User(username, email, passwordHash);
        userDao.save(user);
        System.out.println("Registered successfully: " + username);
        return true;
    }

    public boolean login(String username, String password) {
        Optional<User> userOpt = userDao.findByUsername(username);

        if (userOpt.isEmpty()) {
            System.out.println("User not found: " + username);
            return false;
        }

        User user = userOpt.get();

        // Verify password against stored hash
        BCrypt.Result result = BCrypt.verifyer().verify(
                password.toCharArray(),
                user.getPasswordHash()
        );

        if (result.verified) {
            loggedInUser = user;
            System.out.println("Login successful! Welcome, " + username);
            return true;
        } else {
            System.out.println("Incorrect password.");
            return false;
        }
    }

    public void logout() {
        if (loggedInUser != null) {
            System.out.println("Goodbye, " + loggedInUser.getUsername());
            loggedInUser = null;
        }
    }

    public boolean isLoggedIn() {
        return loggedInUser != null;
    }

    public User getLoggedInUser() {
        return loggedInUser;
    }
}