package com.pch.services;

import com.pch.pojo.User;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Chi Hao
 */
public interface UserService {
    List<User> getUsers(Map<String, String> params);
    void addOrUpdateUser(User u);
    User getUserById(int id);
    void deleteUser(int id);
}
