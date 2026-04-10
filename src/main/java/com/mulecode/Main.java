package com.mulecode;

import com.mulecode.dao.NoteDaoImpl;
import com.mulecode.dao.UserDaoImpl;
import com.mulecode.db.DatabaseConnection;
import com.mulecode.model.Note;
import com.mulecode.model.User;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DatabaseConnection.getConnection();

            UserDaoImpl userDao = new UserDaoImpl();
            NoteDaoImpl noteDao = new NoteDaoImpl();

            // Test: Create a user
            User user = new User("mule2020", "mulgashaw12@gmail.com", "hashedpassword123");
            userDao.save(user);
            System.out.println("Created: " + user);

            // Test: Find the user
            userDao.findById(user.getId()).ifPresent(u ->
                    System.out.println("Found user: " + u));

            // Test: Create a note
            Note note = new Note("My First Note", "This is the content of my first note.", user.getId());
            noteDao.save(note);
            System.out.println("Created: " + note);

            // Test: Find all notes for this user
            List<Note> notes = noteDao.findByUserId(user.getId());
            System.out.println("Notes for user: " + notes.size());

            // Test: Update the note
            note.setTitle("My Updated Note");
            noteDao.update(note);

            // Test: Delete the note
            noteDao.delete(note.getId());
            System.out.println("Note deleted successfully");

            // Test: Delete the user
            userDao.delete(user.getId());
            System.out.println("User deleted successfully");

            DatabaseConnection.closeConnection();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}