package web.service;

import web.model.User;
import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    void addUser(User user);
    void editUser(String firstName,
                  String secondName,
                  Integer age,
                  Long id);
    void removeUser(Long id);
    User getUserById(Long id);
}
