package com.sugar.care.services;

import com.sugar.care.entities.User;
import com.sugar.care.entities.UserAccount;
import com.sugar.care.exceptions.ResourceAlreadyExistsException;
import com.sugar.care.exceptions.ResourceNotFoundException;
import com.sugar.care.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public Optional<User> getUserByID(long id) {
        return userRepo.findById(id);
    }

    public User createUser(User user) {
        User newUser;
        Optional<User> userOptional = userRepo.findByPhoneNumber(user.getPhoneNumber());
        if (userOptional.isPresent()) {
            throw new ResourceAlreadyExistsException("user with phoneNumber-" + user.getPhoneNumber() + " exists");
        } else {
            //One-to-One bidirectional mapping
            UserAccount account = new UserAccount();
            account.setUser(user);
            user.setAccount(account);
            newUser = userRepo.save(user);
        }
        return newUser;
    }

    public User updateUser(long id, User updatedUser) {
        return userRepo.findById(id).map(user -> {
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setPassword(updatedUser.getPassword());
            return userRepo.save(user);
        }).orElseThrow(() -> new ResourceNotFoundException("userId-" + id + " does not exist"));
    }

    public void deleteUser(long id) {
        Optional<User> optionalUser = userRepo.findById(id);
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("userId-" + id + " does not exist");
        } else {
            userRepo.deleteById(id);
        }
    }

}
