package com.timeyang.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

/**
 * @author yangck
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/user")
    public Principal user(Principal user) {
        return user;
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
