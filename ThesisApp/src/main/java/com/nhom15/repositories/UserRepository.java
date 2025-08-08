package com.nhom15.repositories;

import com.nhom15.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chi Hao
 */
public interface UserRepository {
    List<User> getUsers(Map<String, String> params);
    User getUserById(int id);
    void addOrUpdateUser(User u);
    void deleteUser(int id);
    User getUserByUsername(String username);
    boolean authenticate(String username, String password);
    List<User> getUsersByRole(String role);
}
