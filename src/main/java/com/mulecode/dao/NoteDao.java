package com.mulecode.dao;

import com.mulecode.model.Note;
import java.util.List;
import java.util.Optional;

public interface NoteDao {
    void save(Note note);
    Optional<Note> findById(int id);
    List<Note> findByUserId(int userId);
    List<Note> findAll();
    void update(Note note);
    void delete(int id);
}