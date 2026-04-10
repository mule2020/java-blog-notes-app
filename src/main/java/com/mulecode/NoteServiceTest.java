package com.mulecode;

import com.mulecode.auth.AuthService;
import com.mulecode.db.DatabaseConnection;
import com.mulecode.model.Note;
import com.mulecode.service.NoteService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import java.sql.SQLException;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NoteServiceTest {

    private static AuthService authService;
    private static NoteService noteService;
    private static int createdNoteId;

    @BeforeAll
    static void setup() throws SQLException {
        DatabaseConnection.getConnection();
        authService = new AuthService();
        noteService = new NoteService(authService);
        authService.login("testuser", "testpass123");
    }

    @AfterAll
    static void teardown() {
        DatabaseConnection.closeConnection();
    }

    @Test
    @Order(1)
    void testCreateNote() {
        noteService.createNote("Test Note", "Test content");
        List<Note> notes = noteService.getMyNotes();
        Assertions.assertFalse(notes.isEmpty(), "Notes list should not be empty");
        createdNoteId = notes.get(0).getId();
    }

    @Test
    @Order(2)
    void testGetMyNotes() {
        List<Note> notes = noteService.getMyNotes();
        Assertions.assertNotNull(notes, "Notes should not be null");
        Assertions.assertTrue(notes.size() > 0, "Should have at least one note");
    }

    @Test
    @Order(3)
    void testUpdateNote() {
        List<Note> notes = noteService.getMyNotes();
        int noteId = notes.get(0).getId();
        noteService.updateNote(noteId, "Updated Title", "Updated content");
        List<Note> updated = noteService.getMyNotes();
        Assertions.assertEquals("Updated Title", updated.get(0).getTitle());
    }

    @Test
    @Order(4)
    void testCreateNoteWithoutLogin() {
        authService.logout();
        noteService.createNote("Should Fail", "No login");
        List<Note> notes = noteService.getMyNotes();
        Assertions.assertTrue(notes.isEmpty(), "Should not create note when logged out");
    }
}