package com.mulecode.service;

import com.mulecode.auth.AuthService;
import com.mulecode.dao.NoteDao;
import com.mulecode.dao.NoteDaoImpl;
import com.mulecode.model.Note;

import java.util.List;
import java.util.Optional;

public class NoteService {

    private final NoteDao noteDao;
    private final AuthService authService;

    public NoteService(AuthService authService) {
        this.noteDao = new NoteDaoImpl();
        this.authService = authService;
    }

    public void createNote(String title, String content) {
        if (!authService.isLoggedIn()) {
            System.out.println("You must be logged in to create a note.");
            return;
        }
        int userId = authService.getLoggedInUser().getId();
        Note note = new Note(title, content, userId);
        noteDao.save(note);
    }

    public List<Note> getMyNotes() {
        if (!authService.isLoggedIn()) {
            System.out.println("You must be logged in to view notes.");
            return List.of();
        }
        int userId = authService.getLoggedInUser().getId();
        return noteDao.findByUserId(userId);
    }

    public void updateNote(int noteId, String newTitle, String newContent) {
        if (!authService.isLoggedIn()) {
            System.out.println("You must be logged in to update a note.");
            return;
        }
        Optional<Note> noteOpt = noteDao.findById(noteId);
        if (noteOpt.isEmpty()) {
            System.out.println("Note not found: id=" + noteId);
            return;
        }
        Note note = noteOpt.get();
        if (note.getUserId() != authService.getLoggedInUser().getId()) {
            System.out.println("You can only update your own notes.");
            return;
        }
        note.setTitle(newTitle);
        note.setContent(newContent);
        noteDao.update(note);
    }

    public void deleteNote(int noteId) {
        if (!authService.isLoggedIn()) {
            System.out.println("You must be logged in to delete a note.");
            return;
        }
        Optional<Note> noteOpt = noteDao.findById(noteId);
        if (noteOpt.isEmpty()) {
            System.out.println("Note not found: id=" + noteId);
            return;
        }
        Note note = noteOpt.get();
        if (note.getUserId() != authService.getLoggedInUser().getId()) {
            System.out.println("You can only delete your own notes.");
            return;
        }
        noteDao.delete(noteId);
    }
}