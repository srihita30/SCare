package com.sugar.care.services;

import com.sugar.care.entities.User;
import com.sugar.care.entities.UserAccount;
import com.sugar.care.entities.UserDailyUpdate;
import com.sugar.care.enums.AlertColour;
import com.sugar.care.enums.DiabeticState;
import com.sugar.care.exceptions.ResourceNotFoundException;
import com.sugar.care.repositories.AccountRepository;
import com.sugar.care.repositories.DailyUpdatesRepository;
import com.sugar.care.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private DailyUpdatesRepository updatesRepo;

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
            accountUpdated.setHeightInFeet(account.getHeightInFeet());
            accountUpdated.setHeightInInches(account.getHeightInInches());
            accountUpdated.setWeight(account.getWeight());
            accountUpdated.setHasHyperTension(account.isHasHyperTension());
            accountUpdated.setCardiacPatient(account.isCardiacPatient());
            accountUpdated.setCardiacSurgeryDone(account.isCardiacSurgeryDone());
            accountUpdated.setDiabeticStage(account.getDiabeticStage());
            accountUpdated.setYearDetected(account.getYearDetected());
            return accountRepo.save(accountUpdated);
        }).orElseThrow(() -> new ResourceNotFoundException("Account for userId-" + userId + " does not exist"));
    }

    public List<UserAccount> getAllAccount() {
        return accountRepo.findAll();
    }

    public void updateConsultDoctorStatus(long userId) {
        LocalDate date = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 30, Sort.by("recordDate"));
        UserAccount account = accountRepo.getOne(userId);
        boolean consult = false;
        List<UserDailyUpdate> recordList;
        if (account.getDiabeticStage() == DiabeticState.DIABETIC) {
            recordList = updatesRepo.getRecordsForDateRange(userId, date.minusDays(5), date, pageable);
            if (recordList.size() == 0) return;
            boolean red = true;
            boolean reading = false;
            for (UserDailyUpdate u : recordList) {
                if (u.getColourStatus() != AlertColour.RED) {
                    red = false;
                }
                if (u.getSugarReadingValue() > 300) {
                    reading = true;
                }
            }
            UserAccount acc = getAccount(userId);
            acc.setShouldConsultDoctor(red || reading);
            updateAccount(userId, acc);
        } else {
            recordList = updatesRepo.getRecordsForDateRange(userId, date.minusDays(3), date, pageable);
            if (recordList.size() == 0) return;
            boolean amber = true;
            boolean red = false;
            for (UserDailyUpdate u : recordList) {
                if (u.getColourStatus() != AlertColour.AMBER) {
                    amber = false;
                }
                if (u.getColourStatus() == AlertColour.RED) {
                    red = true;
                }
            }
            UserAccount acc = getAccount(userId);
            acc.setShouldConsultDoctor(amber || red);
            updateAccount(userId, acc);
        }
    }

}
