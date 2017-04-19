package com.timeyang.api.v1;

import com.timeyang.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}