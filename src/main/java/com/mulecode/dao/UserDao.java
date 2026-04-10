package com.mulecode.dao;

import com.mulecode.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    void save(User user);
    Optional<User> findById(int id);
    Optional<User> findByUsername(String username);
    List<User> findAll();
    void update(User user);
    void delete(int id);
}