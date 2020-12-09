package ru.itsinfo.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.itsinfo.dao.UserDAO;
import ru.itsinfo.model.Role;
import ru.itsinfo.model.User;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Qualifier("userServiceImpl")
public class UserServiceimpl implements UserService, UserDetailsService {

    private final UserDAO userDAO;

    public UserServiceimpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public List<User> readAllUsers() {
        return userDAO.readAllUsers();
    }

    @Override
    public User createUser(User user) {
        return userDAO.createUser(user);
    }

    @Override
    public User deleteUser(Integer userId) {
        try {
            return userDAO.deleteUser(userId);
        } catch (InvalidDataAccessApiUsageException e) {
            System.out.println("Not found User");
        }
        return null;
    }

    @Override
    public User updateUser(User user) {
        return userDAO.updateUser(user);
    }

    @Override
    public User readUser(Integer userId) {
        return userDAO.readUser(userId);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = new User();
        user.setEmail("111");
        user.setPassword("111");
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1, "ROLE_USER"));
//        roles.add(new Role(2, "ROLE_ADMIN"));
        user.setRoles(roles);

        return user;
    }
}
