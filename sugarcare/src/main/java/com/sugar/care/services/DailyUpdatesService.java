package com.sugar.care.services;

import com.sugar.care.entities.User;
import com.sugar.care.entities.UserDailyUpdate;
import com.sugar.care.exceptions.ResourceNotFoundException;
import com.sugar.care.repos.DailyUpdatesRepository;
import com.sugar.care.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DailyUpdatesService {

    @Autowired
    private DailyUpdatesRepository dailyUpdatesRepo;

    @Autowired
    private UserRepository userRepo;

    public List<UserDailyUpdate> getUpdatesByUserId(long user_id, int number_of_days) {
        LocalDate startDate = LocalDate.now().minusDays(number_of_days);
        LocalDate endDate = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 30, Sort.by("recordDate"));
        return dailyUpdatesRepo.getRecordsForDateRange(user_id, startDate, endDate, pageable);
    }

    public UserDailyUpdate createRecord(long user_id, UserDailyUpdate newRecord) {
        //if record exists for the recordDate -- update it
        if (dailyUpdatesRepo.existsByRecordDate(user_id, newRecord.getRecordDate())) {
            return updateRecord(user_id, newRecord);
        }
        //else create
        User user = userRepo.getOne(user_id);
        newRecord.setUser(user);
        return dailyUpdatesRepo.save(newRecord);
    }

    public void deleteRecord(long record_id) {
        if (!dailyUpdatesRepo.existsById(record_id)) {
            throw new ResourceNotFoundException("Record for recordId-" + record_id + " does not exist");
        }
        dailyUpdatesRepo.deleteById(record_id);
    }

    public UserDailyUpdate updateRecord(long user_id, UserDailyUpdate updateRecord) {
        //if record not exists for the recordDate
        if (!dailyUpdatesRepo.existsByRecordDate(user_id, updateRecord.getRecordDate())) {
            //create it
            return createRecord(user_id, updateRecord);
        }
        //else update it
        UserDailyUpdate existingRecord = dailyUpdatesRepo.getRecordByRecordDate(user_id, updateRecord.getRecordDate()).get(0);
        existingRecord.setHB1ACvalue(updateRecord.getHB1ACvalue());
        existingRecord.setReadingTakenAfterMeal(updateRecord.isReadingTakenAfterMeal());
        existingRecord.setSugarReadingValue(updateRecord.getSugarReadingValue());
        existingRecord.setHB1ACvalue(updateRecord.getHB1ACvalue());
        existingRecord.setRecordDate(updateRecord.getRecordDate());
        return dailyUpdatesRepo.save(existingRecord);
    }

}
