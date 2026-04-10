package com.mulecode;

import com.mulecode.db.DatabaseConnection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
            System.out.println("Blog Notes App — starting up...");
            DatabaseConnection.closeConnection();
        } catch (SQLException e) {
            System.out.println("Failed to connect: " + e.getMessage());
        }
    }
}