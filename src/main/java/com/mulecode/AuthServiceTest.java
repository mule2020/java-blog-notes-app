package com.mulecode;

import com.mulecode.auth.AuthService;
import com.mulecode.db.DatabaseConnection;
//import org.junit.jupiter.api.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.sql.SQLException;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthServiceTest {

    private static AuthService authService;

    @BeforeAll
    static void setup() throws SQLException {
        DatabaseConnection.getConnection();
        authService = new AuthService();
    }

    @AfterAll
    static void teardown() {
        DatabaseConnection.closeConnection();
    }

    @Test
    @Order(1)
    void testRegisterSuccess() {
        boolean result = authService.register(
                "testuser", "test@gmail.com", "testpass123"
        );
        Assertions.assertTrue(result, "Registration should succeed");
    }

    @Test
    @Order(2)
    void testRegisterDuplicateUsername() {
        boolean result = authService.register(
                "testuser", "other@gmail.com", "testpass123"
        );
        Assertions.assertFalse(result, "Duplicate username should fail");
    }

    @Test
    @Order(3)
    void testLoginWrongPassword() {
        boolean result = authService.login("testuser", "wrongpassword");
        Assertions.assertFalse(result, "Wrong password should fail");
    }

    @Test
    @Order(4)
    void testLoginSuccess() {
        boolean result = authService.login("testuser", "testpass123");
        Assertions.assertTrue(result, "Correct password should succeed");
    }

    @Test
    @Order(5)
    void testIsLoggedIn() {
        authService.login("testuser", "testpass123");
        Assertions.assertTrue(authService.isLoggedIn(), "User should be logged in");
    }

    @Test
    @Order(6)
    void testLogout() {
        authService.login("testuser", "testpass123");
        authService.logout();
        Assertions.assertFalse(authService.isLoggedIn(), "User should be logged out");
    }
}