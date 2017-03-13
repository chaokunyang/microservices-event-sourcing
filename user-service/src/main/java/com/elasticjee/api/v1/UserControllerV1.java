package com.elasticjee.api.v1;

import com.elasticjee.user.User;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.ObjPtr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

/**
 * @author yangck
 */
@RestController
@RequestMapping("/v1")
public class UserControllerV1 {

    @Autowired
    private UserServiceV1 userService;

    @RequestMapping(path = "/me")
    public ResponseEntity<User> me(Principal principal) {
        User user = null;
        if(principal != null) {
            user = userService.getUserByUsername(principal.getName());
        }
        return Optional.ofNullable(user)
                .map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @RequestMapping(path = "/users", method = RequestMethod.POST, name = "createUser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Assert.notNull(user);
        return Optional.ofNullable(userService.createUser(user))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.CONFLICT));
    }

    @RequestMapping(path = "users/{id}", method = RequestMethod.GET, name = "getUser")
    public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
        return Optional.ofNullable(userService.getUserById(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "users/{id}", method = RequestMethod.PUT, name = "updateUser")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) {
        Assert.notNull(user);
        user.setId(id);
        return Optional.ofNullable(userService.updateUser(id, user))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(path = "users/{id}", method = RequestMethod.DELETE,
            name = "deleteUser")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        return Optional.ofNullable(userService.deleteUser(id))
                .map(result -> new ResponseEntity<>(result, HttpStatus.NO_CONTENT))
                .orElse(new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND));
    }
}