package web.service;

import web.model.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void addUser(User user);
    void editUser(User user);
    void removeUser(Long id);
    List<User> searchUsersByParams(User searchParams);
    User getUserById(Long id);
}
