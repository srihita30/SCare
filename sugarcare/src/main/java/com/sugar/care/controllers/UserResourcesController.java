package com.sugar.care.controllers;

import com.sugar.care.entities.User;
import com.sugar.care.exceptions.ResourceNotFoundException;
import com.sugar.care.security.CustomUserDetails;
import com.sugar.care.security.utility.JwtTokenProvider;
import com.sugar.care.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.sugar.care.constants.SecurityConstants.JWT_TOKEN_HEADER;
import static com.sugar.care.constants.SecurityConstants.TOKEN_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping(value = "/user")
@Api(produces = "application/json", value = "Deals with user related work")
public class UserResourcesController {


    private UserService userService;

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private HttpServletRequest request;


    @Autowired
    public UserResourcesController(AuthenticationManager authenticationManager, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

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

    @PostMapping(value = "/register")
    public ResponseEntity<Object> createUser(@RequestBody User user) {
        User newUser = userService.createUser(user);
        URI uriLocation = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .build(newUser.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody User user){
        authenticate(user.getPhoneNumber(), user.getPassword());
        User loginUser = userService.findByPhoneNumber(user.getPhoneNumber());
        CustomUserDetails userPrincipal = new CustomUserDetails(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, OK);
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<Object> authenticate(){
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
            return ResponseEntity.status(NOT_ACCEPTABLE).build();
        }
        String token = authorizationHeader.substring(TOKEN_PREFIX.length());
        boolean expired = jwtTokenProvider.isTokenExpired(token);
        if(expired){
            return ResponseEntity.status(NOT_ACCEPTABLE).build();
        }
        User userOptional = userService.findByPhoneNumber(jwtTokenProvider.getSubject(token));

        return ResponseEntity.ok(userOptional);
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

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(CustomUserDetails user) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(user));
        return headers;
    }
}
