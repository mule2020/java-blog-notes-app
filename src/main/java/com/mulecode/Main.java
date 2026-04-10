package com.mulecode;

import com.mulecode.db.DatabaseConnection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();
            AppMenu menu = new AppMenu();
            menu.start();
        } catch (SQLException e) {
            System.out.println("Failed to connect to database: " + e.getMessage());
        }
    }
}