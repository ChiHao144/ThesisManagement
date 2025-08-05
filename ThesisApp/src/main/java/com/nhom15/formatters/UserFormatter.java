package com.nhom15.formatters;

import com.nhom15.pojo.User;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author Chi Hao
 */
public class UserFormatter implements Formatter<User>{

    @Override
    public String print(User u, Locale locale) {
        return String.valueOf(u.getId());
    }

    @Override
    public User parse(String userId, Locale locale) throws ParseException {
        User u = new User(Integer.valueOf(userId)); 
        return u;
    }
    
}
