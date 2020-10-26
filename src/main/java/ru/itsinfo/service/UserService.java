package ru.itsinfo.service;

import ru.itsinfo.model.User;

import java.util.List;

public interface UserService {
    List<User> readAllUsers();

    User createUser(User user);

    User deleteUser(Integer userId);

    User updateUser(User user);

    User readUser(Integer userId);
}
