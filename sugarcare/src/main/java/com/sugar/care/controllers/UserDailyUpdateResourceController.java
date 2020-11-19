package com.sugar.care.controllers;

import com.sugar.care.entities.UserDailyUpdate;
import com.sugar.care.services.AccountService;
import com.sugar.care.services.DailyUpdatesService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/user-daily-records")
@Api(produces = "application/json", value = "Deals with user's daily sugar records related work")
public class UserDailyUpdateResourceController {

    @Autowired
    private DailyUpdatesService updateService;

    @Autowired
    private AccountService accountService;

    //create record
    @PostMapping(value = "/user/{user_id}")
    public ResponseEntity<UserDailyUpdate> createRecord(@PathVariable long user_id, @RequestBody UserDailyUpdate newUpdate) {
        UserDailyUpdate createdRecord = updateService.createRecord(user_id, newUpdate);
        accountService.updateConsultDoctorStatus(user_id);
        URI uriLocation = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .build(createdRecord.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    //update record
    @PutMapping(value = "/user/{user_id}")
    public ResponseEntity<UserDailyUpdate> updateRecord(@PathVariable long user_id, @RequestBody UserDailyUpdate updateBody) {
        UserDailyUpdate updatedRecord = updateService.updateRecord(user_id, updateBody);
        accountService.updateConsultDoctorStatus(user_id);
        return ResponseEntity.accepted().body(updatedRecord);
    }

    //delete record
    @DeleteMapping(value = "/record/{record_id}")
    public ResponseEntity<UserDailyUpdate> deleteRecord(@PathVariable long record_id) {
        updateService.deleteRecord(record_id);
        return ResponseEntity.ok().build();
    }


    //get last n days record
    @GetMapping(value = "/user/{user_id}/for-days/{number_of_days}")
    public ResponseEntity<List<UserDailyUpdate>> getLastNDaysRecords(@PathVariable long user_id, @PathVariable int number_of_days) {
        List<UserDailyUpdate> records = updateService.getUpdatesByUserId(user_id, number_of_days);
        return ResponseEntity.ok(records);
    }
}
