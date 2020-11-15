package com.sugar.care.services;

import com.sugar.care.entities.User;
import com.sugar.care.entities.UserAccount;
import com.sugar.care.exceptions.ResourceNotFoundException;
import com.sugar.care.repos.AccountRepository;
import com.sugar.care.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private UserRepository userRepo;

    //create account
    public UserAccount createAccount(long user_id, UserAccount account) {
        Optional<UserAccount> userAccountOptional = accountRepo.findById(user_id);
        if (userAccountOptional.isEmpty()) {
            User user = userRepo.getOne(user_id);
            account.setUser(user);
            return accountRepo.save(account);
        }
        return updateAccount(user_id, account);
    }

    public UserAccount getAccount(long accountId) {
        Optional<UserAccount> accountOptional = accountRepo.findById(accountId);
        if (accountOptional.isEmpty()) {
            throw new ResourceNotFoundException("accountId-" + accountId + " does not exist");
        }
        return accountOptional.get();
    }


    public UserAccount updateAccount(long userId, UserAccount account) {
        return accountRepo.findById(userId).map(accountUpdated -> {
            accountUpdated.setAge(account.getAge());
            accountUpdated.setGender(account.getGender());
            accountUpdated.setHeight(account.getHeight());
            accountUpdated.setWeight(account.getWeight());
            accountUpdated.setHasHyperTension(account.isHasHyperTension());
            accountUpdated.setCardiacPatient(account.isCardiacPatient());
            accountUpdated.setCardiacSurgeryDone(account.isCardiacSurgeryDone());
            accountUpdated.setSugarLevel(account.getSugarLevel());
            accountUpdated.setYearDetected(account.getYearDetected());
            return accountRepo.save(accountUpdated);
        }).orElseThrow(() -> new ResourceNotFoundException("Account for userId-" + userId + " does not exist"));
    }

    public List<UserAccount> getAllAccount() {
        return accountRepo.findAll();
    }


}
