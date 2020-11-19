package com.sugar.care.repositories;

import com.sugar.care.entities.User;
import com.sugar.care.entities.UserDailyUpdate;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyUpdatesRepository extends JpaRepository<UserDailyUpdate, Long> {
    Optional<User> findByUserId(long user_id);

    @Query(value = "SELECT updates FROM UserDailyUpdate updates WHERE updates.user.id =?1 AND updates.recordDate BETWEEN ?2 AND ?3")
    List<UserDailyUpdate> getRecordsForDateRange(long user_id, LocalDate start_date, LocalDate end_date, Pageable pageable);

    @Query(value = "SELECT CASE WHEN (count(record) > 0) THEN true ELSE false END FROM UserDailyUpdate record where record.user.id =?1 AND record.recordDate =?2")
    Boolean existsByRecordDate(long user_id, LocalDate record_date);

    @Query(value = "SELECT record FROM UserDailyUpdate record where record.user.id =?1 AND record.recordDate =?2")
    List<UserDailyUpdate> getRecordByRecordDate(long user_id, LocalDate record_date);
}
