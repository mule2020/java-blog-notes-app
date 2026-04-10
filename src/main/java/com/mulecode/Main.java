package com.mulecode;

import com.mulecode.auth.AuthService;
import com.mulecode.db.DatabaseConnection;
import com.mulecode.model.Note;
import com.mulecode.service.NoteService;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();

            AuthService authService = new AuthService();
            NoteService noteService = new NoteService(authService);

            // Test: Register a new user
            System.out.println("\n--- REGISTER ---");
            authService.register("mule2020", "mulgashaw12@gmail.com", "mypassword123");

            // Test: Try registering same username again
            authService.register("mule2020", "other@gmail.com", "anotherpassword");

            // Test: Login with wrong password
            System.out.println("\n--- LOGIN ---");
            authService.login("mule2020", "wrongpassword");

            // Test: Login with correct password
            authService.login("mule2020", "mypassword123");

            // Test: Create notes
            System.out.println("\n--- CREATE NOTES ---");
            noteService.createNote("My First Note", "This is my first note content.");
            noteService.createNote("My Second Note", "This is my second note content.");

            // Test: View all my notes
            System.out.println("\n--- MY NOTES ---");
            List<Note> notes = noteService.getMyNotes();
            notes.forEach(n -> System.out.println(n));

            // Test: Update a note
            System.out.println("\n--- UPDATE NOTE ---");
            noteService.updateNote(notes.get(0).getId(), "Updated Title", "Updated content.");

            // Test: Delete a note
            System.out.println("\n--- DELETE NOTE ---");
            noteService.deleteNote(notes.get(1).getId());

            // Test: View notes after delete
            System.out.println("\n--- MY NOTES AFTER DELETE ---");
            noteService.getMyNotes().forEach(n -> System.out.println(n));

            // Test: Logout
            System.out.println("\n--- LOGOUT ---");
            authService.logout();

            // Test: Try creating note after logout
            System.out.println("\n--- TRY CREATE NOTE AFTER LOGOUT ---");
            noteService.createNote("Should Fail", "This should not be created.");

            DatabaseConnection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}