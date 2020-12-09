package ru.itsinfo.dao;

import ru.itsinfo.model.User;

import java.util.List;

public interface UserDAO {
    List<User> readAllUsers();

    User createUser(User user);

    User deleteUser(Integer userId);

    User updateUser(User user);

    User readUser(Integer userId);

    User loadUserByUsername(String email);
}
