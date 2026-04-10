package com.mulecode.dao;

import com.mulecode.db.DatabaseConnection;
import com.mulecode.model.Note;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoteDaoImpl implements NoteDao {

    @Override
    public void save(Note note) {
        String sql = "INSERT INTO notes (title, content, user_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getContent());
            stmt.setInt(3, note.getUserId());
            stmt.executeUpdate();

            ResultSet keys = stmt.getGeneratedKeys();
            if (keys.next()) {
                note.setId(keys.getInt(1));
            }
            System.out.println("Note saved: " + note.getTitle());

        } catch (SQLException e) {
            System.out.println("Error saving note: " + e.getMessage());
        }
    }

    @Override
    public Optional<Note> findById(int id) {
        String sql = "SELECT * FROM notes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error finding note: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Note> findByUserId(int userId) {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes WHERE user_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notes.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching notes: " + e.getMessage());
        }
        return notes;
    }

    @Override
    public List<Note> findAll() {
        List<Note> notes = new ArrayList<>();
        String sql = "SELECT * FROM notes";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notes.add(mapRow(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error fetching notes: " + e.getMessage());
        }
        return notes;
    }

    @Override
    public void update(Note note) {
        String sql = "UPDATE notes SET title = ?, content = ?, updated_at = CURRENT_TIMESTAMP WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, note.getTitle());
            stmt.setString(2, note.getContent());
            stmt.setInt(3, note.getId());
            stmt.executeUpdate();
            System.out.println("Note updated: " + note.getTitle());

        } catch (SQLException e) {
            System.out.println("Error updating note: " + e.getMessage());
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM notes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Note deleted: id=" + id);

        } catch (SQLException e) {
            System.out.println("Error deleting note: " + e.getMessage());
        }
    }

    private Note mapRow(ResultSet rs) throws SQLException {
        Note note = new Note();
        note.setId(rs.getInt("id"));
        note.setTitle(rs.getString("title"));
        note.setContent(rs.getString("content"));
        note.setUserId(rs.getInt("user_id"));
        note.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        note.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return note;
    }
}