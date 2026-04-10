package com.mulecode;

import com.mulecode.auth.AuthService;
import com.mulecode.model.Note;
import com.mulecode.service.NoteService;

import java.util.List;
import java.util.Scanner;

public class AppMenu {

    private final AuthService authService;
    private final NoteService noteService;
    private final Scanner scanner;

    public AppMenu() {
        this.authService = new AuthService();
        this.noteService = new NoteService(authService);
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        System.out.println("=============================");
        System.out.println("   Welcome to Blog Notes App  ");
        System.out.println("=============================");

        boolean running = true;
        while (running) {
            if (!authService.isLoggedIn()) {
                showGuestMenu();
            } else {
                showUserMenu();
            }
        }
    }

    private void showGuestMenu() {
        System.out.println("\n--- MAIN MENU ---");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("0. Exit");
        System.out.print("Choose: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> handleRegister();
            case "2" -> handleLogin();
            case "0" -> {
                System.out.println("Goodbye!");
                System.exit(0);
            }
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private void showUserMenu() {
        System.out.println("\n--- NOTES MENU --- (logged in as: "
                + authService.getLoggedInUser().getUsername() + ")");
        System.out.println("1. View my notes");
        System.out.println("2. Create a note");
        System.out.println("3. Update a note");
        System.out.println("4. Delete a note");
        System.out.println("0. Logout");
        System.out.print("Choose: ");

        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1" -> handleViewNotes();
            case "2" -> handleCreateNote();
            case "3" -> handleUpdateNote();
            case "4" -> handleDeleteNote();
            case "0" -> authService.logout();
            default -> System.out.println("Invalid option. Try again.");
        }
    }

    private void handleRegister() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        authService.register(username, email, password);
    }

    private void handleLogin() {
        System.out.print("Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Password: ");
        String password = scanner.nextLine().trim();
        authService.login(username, password);
    }

    private void handleViewNotes() {
        List<Note> notes = noteService.getMyNotes();
        if (notes.isEmpty()) {
            System.out.println("You have no notes yet.");
        } else {
            System.out.println("\n--- YOUR NOTES ---");
            notes.forEach(n -> {
                System.out.println("ID: " + n.getId()
                        + " | Title: " + n.getTitle());
                System.out.println("   " + n.getContent());
                System.out.println("   Created: " + n.getCreatedAt());
                System.out.println();
            });
        }
    }

    private void handleCreateNote() {
        System.out.print("Title: ");
        String title = scanner.nextLine().trim();
        System.out.print("Content: ");
        String content = scanner.nextLine().trim();
        noteService.createNote(title, content);
    }

    private void handleUpdateNote() {
        handleViewNotes();
        System.out.print("Enter note ID to update: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        System.out.print("New title: ");
        String title = scanner.nextLine().trim();
        System.out.print("New content: ");
        String content = scanner.nextLine().trim();
        noteService.updateNote(id, title, content);
    }

    private void handleDeleteNote() {
        handleViewNotes();
        System.out.print("Enter note ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine().trim());
        noteService.deleteNote(id);
    }
}