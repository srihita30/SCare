package com.sugar.care.controllers;

import com.sugar.care.entities.User;
import com.sugar.care.exceptions.ResourceNotFoundException;
import com.sugar.care.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class UserResourcesController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users =  userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "/users/{id}")
    public ResponseEntity<User> getUserByID(@PathVariable long id) {
        Optional<User> userOptional = userService.getUserByID(id);
        if (userOptional.isEmpty()) {
            throw new ResourceNotFoundException("userId-" + id + " does not exist");
        }
        User user = userOptional.get();
        return ResponseEntity.ok(user);
    }

    @PostMapping(value = "/users")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        URI uriLocation = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .build(newUser.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @PutMapping(value = "/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable long id, @RequestBody User updatUser) {
        User updatedUser = userService.updateUser(id, updatUser);
        return ResponseEntity.accepted().body(updatedUser);
    }

    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }
}
