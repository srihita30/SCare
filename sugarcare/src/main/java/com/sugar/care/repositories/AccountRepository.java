package com.sugar.care.repositories;

import com.sugar.care.entities.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<UserAccount,Long> {
    //public Optional<UserAccount> findByUserId(long userId);
}
