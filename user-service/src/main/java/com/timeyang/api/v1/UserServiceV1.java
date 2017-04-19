package com.timeyang.api.v1;

import com.timeyang.user.User;
import com.timeyang.user.UserRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangck
 */
@Service
public class UserServiceV1 {

    @Autowired
    private UserRepository userRepository;

    //@Cacheable(value = "user", key = "#username") // Cannot get Jedis connection ?
    @HystrixCommand
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

}

