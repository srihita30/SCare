package com.sugar.care.services;

import com.sugar.care.entities.User;
import com.sugar.care.entities.UserDailyUpdate;
import com.sugar.care.enums.AlertColour;
import com.sugar.care.exceptions.BadDataException;
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
import java.util.ArrayList;
import java.util.List;

@Service
public class DailyUpdatesService {

    @Autowired
    private DailyUpdatesRepository dailyUpdatesRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private AccountRepository accountRepo;

    public List<DailyUpdateResponse> getUpdatesByUserId(long user_id, int number_of_days) {
        LocalDate startDate = LocalDate.now().minusDays(number_of_days);
        LocalDate endDate = LocalDate.now();
        Pageable pageable = PageRequest.of(0, 60, Sort.by("recordDate").descending());
        List<UserDailyUpdate> temporaryList =  dailyUpdatesRepo.getRecordsForDateRange(user_id, startDate, endDate, pageable);
        List<DailyUpdateResponse> resultList = new ArrayList<>();
        converter(temporaryList,resultList);
        return resultList;
    }

    public UserDailyUpdate createRecord(long user_id, UserDailyUpdate newRecord) {
        //if record exists for the recordDate -- update it
        if (dailyUpdatesRepo.existsByRecordDate(user_id, newRecord.getRecordDate() , newRecord.getIsReadingTakenAfterMeal())) {
            return updateRecord(user_id, newRecord);
        }
        //else create
        User user = userRepo.findById(user_id).get();
        newRecord.setUser(user);
        newRecord.setColourStatus(getColourBasedOnReadingsForDailyUpdates(newRecord));
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
        if (!dailyUpdatesRepo.existsByRecordDate(user_id, updateRecord.getRecordDate(), updateRecord.getIsReadingTakenAfterMeal())) {
            //create it
            return createRecord(user_id, updateRecord);
        }
        //else update it
        UserDailyUpdate existingRecord = dailyUpdatesRepo.getRecordByRecordDate(user_id, updateRecord.getRecordDate(),updateRecord.getIsReadingTakenAfterMeal()).get(0);
        existingRecord.setHB1ACvalue(updateRecord.getHB1ACvalue());
        existingRecord.setReadingTakenAfterMeal(updateRecord.getIsReadingTakenAfterMeal());
        existingRecord.setSugarReadingValue(updateRecord.getSugarReadingValue());
        existingRecord.setHB1ACvalue(updateRecord.getHB1ACvalue());
        existingRecord.setRecordDate(updateRecord.getRecordDate());
        existingRecord.setColourStatus(getColourBasedOnReadingsForDailyUpdates(updateRecord));
        return dailyUpdatesRepo.save(existingRecord);
    }

    private AlertColour getColourBasedOnReadingsForDailyUpdates(UserDailyUpdate dailyRecord) {
        boolean fasting = dailyRecord.getIsReadingTakenAfterMeal();
        int sugarReading = dailyRecord.getSugarReadingValue();
        AlertColour result = AlertColour.GREEN;
        if (fasting) {
            if (sugarReading < 100) {
                result = AlertColour.GREEN;
            } else if (sugarReading >= 100 && sugarReading <= 125) {
                result = AlertColour.AMBER;
            } else if (sugarReading > 125) {
                result = AlertColour.RED;
            }
        } else {
            if (sugarReading < 140) {
                result = AlertColour.GREEN;
            } else if (sugarReading >= 140 && sugarReading <= 200) {
                result = AlertColour.AMBER;
            } else if (sugarReading > 200) {
                result = AlertColour.RED;
            }
        }
        return result;
    }

    private void converter(List<UserDailyUpdate> userDailyUpdates ,List<DailyUpdateResponse> dailyUpdateResponses){
        if(userDailyUpdates.size() % 2 != 0){
            throw new BadDataException("The Data fetched is not complete : DailyUpdateService");
        }
        for(int i = 0 ; i < userDailyUpdates.size() ; i += 2){
            UserDailyUpdate before = new UserDailyUpdate();
            UserDailyUpdate after = new UserDailyUpdate();
            if(userDailyUpdates.get(i).getIsReadingTakenAfterMeal()){
                after = userDailyUpdates.get(i);
                before = userDailyUpdates.get(i+1);
            }else{
                after = userDailyUpdates.get(i+1);
                before = userDailyUpdates.get(i);
            }
            DailyUpdateResponse obj = new DailyUpdateResponse(before.getSugarReadingValue(),
                    after.getSugarReadingValue(), before.getRecordDate() ,before.getColourStatus(), after.getColourStatus());
            dailyUpdateResponses.add(obj);
        }
    }

    public static class DailyUpdateResponse{
        int readingBeforeMeal;
        int readingAfterMeal;
        LocalDate recordDate;
        AlertColour beforeMeal;
        AlertColour afterMeal;

        public DailyUpdateResponse(int readingBeforeMeal, int readingAfterMeal, LocalDate recordDate, AlertColour beforeMeal, AlertColour afterMeal) {
            this.readingBeforeMeal = readingBeforeMeal;
            this.readingAfterMeal = readingAfterMeal;
            this.recordDate = recordDate;
            this.beforeMeal = beforeMeal;
            this.afterMeal = afterMeal;
        }

        public int getReadingBeforeMeal() {
            return readingBeforeMeal;
        }

        public void setReadingBeforeMeal(int readingBeforeMeal) {
            this.readingBeforeMeal = readingBeforeMeal;
        }

        public int getReadingAfterMeal() {
            return readingAfterMeal;
        }

        public void setReadingAfterMeal(int readingAfterMeal) {
            this.readingAfterMeal = readingAfterMeal;
        }

        public LocalDate getRecordDate() {
            return recordDate;
        }

        public void setRecordDate(LocalDate recordDate) {
            this.recordDate = recordDate;
        }

        public AlertColour getBeforeMeal() {
            return beforeMeal;
        }

        public void setBeforeMeal(AlertColour beforeMeal) {
            this.beforeMeal = beforeMeal;
        }

        public AlertColour getAfterMeal() {
            return afterMeal;
        }

        public void setAfterMeal(AlertColour afterMeal) {
            this.afterMeal = afterMeal;
        }
    }


}
