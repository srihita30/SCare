package com.sugar.care.repos;

import com.sugar.care.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<UserAccount,Long> {
    //public Optional<UserAccount> findByUserId(long userId);
}
