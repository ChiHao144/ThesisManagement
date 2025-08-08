package com.nhom15.services;

import com.nhom15.pojo.User;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;
/**
 *
 * @author Chi Hao
 */
public interface UserService extends UserDetailsService{
    List<User> getUsers(Map<String, String> params);
    void addOrUpdateUser(User u);
    User getUserById(int id);
    void deleteUser(int id);
    User getUserByUsername(String username);
    boolean authenticate(String username, String password);
    
    boolean changePassword(String username, String oldPassword, String newPassword);
}
