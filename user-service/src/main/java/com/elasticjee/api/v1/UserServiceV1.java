package com.elasticjee.api.v1;

import com.elasticjee.user.User;
import com.elasticjee.user.UserRepository;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author yangck
 */
@Service
public class UserServiceV1 {

    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "user", key = "#id")
    @HystrixCommand
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @CacheEvict(value = "user", key = "#user.getId()")
    public User createUser(User user) {
        User result = null;
        if(!userRepository.exists(user.getId())) {
            result = userRepository.save(user);
        }
        return result;
    }

    @Cacheable(value = "user", key = "#id")
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @CachePut(value = "user", key = "#id")
    public User updateUser(Long id, User user) {
        User result = null;
        if(userRepository.exists(user.getId())) {
            result = userRepository.save(user);
        }
        return result;
    }

    @CacheEvict(value = "user", key = "#id")
    public boolean deleteUser(Long id) {
        boolean deleted = false;
        if(userRepository.exists(id)) {
            userRepository.delete(id);
            deleted = true;
        }
        return deleted;
    }
}

