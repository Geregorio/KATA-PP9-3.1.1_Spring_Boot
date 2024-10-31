package web.dao;

import web.model.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();
    void addUser(User user);
    void editUser(User user);
    void removeUser(long id);
    List<User> searchUsers(User searchCriteria);
    User getUserById(long id);

}
