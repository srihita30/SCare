package com.sugar.care.controllers;

import com.sugar.care.entities.UserAccount;
import com.sugar.care.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/account")
public class UserAccountResourceController {

    @Autowired
    private AccountService accountService;

    //create account for userId id
    @PostMapping(value = "/accounts/{user_id}")
    public ResponseEntity<Object> createUserAccount(@RequestBody UserAccount account, @PathVariable long user_id) {
        UserAccount newUserAccount = accountService.createAccount(user_id ,account);
        URI uriLocation = ServletUriComponentsBuilder
                .fromCurrentRequest().build().toUri();
        return ResponseEntity.created(uriLocation).build();
    }

    //get account for userId id
    @GetMapping(value = "/accounts/{user_id}")
    public ResponseEntity<UserAccount> getAccountForUser(@PathVariable long user_id) {
        UserAccount account = accountService.getAccount(user_id);
        return ResponseEntity.ok().body(account);
    }

    //update account for userId id
    @PutMapping(value = "/accounts/{user_id}")
    public ResponseEntity<UserAccount> updateUser(@PathVariable long user_id, @RequestBody UserAccount account) {
        UserAccount updatedAccount = accountService.updateAccount(user_id, account);
        return ResponseEntity.accepted().body(updatedAccount);
    }

    //all accounts
    @GetMapping(value = "/accounts")
    public List<UserAccount> getAllAccount(){
        return accountService.getAllAccount();
    }
}
