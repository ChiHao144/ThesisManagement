package com.pch.repositories;

import com.pch.pojo.User;
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
}
