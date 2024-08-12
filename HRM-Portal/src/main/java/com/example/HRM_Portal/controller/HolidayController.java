package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.entity.HolidayEntity;
import com.example.HRM_Portal.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @PostMapping("/holiday")
    public HolidayEntity createHoliday(@RequestBody HolidayEntity holiday) {
        return holidayService.saveHoliday(holiday);
    }

    @PostMapping("/holidays")
    public List<HolidayEntity> createHolidays(@RequestBody List<HolidayEntity> holidays) {
        return holidayService.saveHolidays(holidays);
    }

    @GetMapping("/holidays")
    public List<HolidayEntity> getHolidays() {
        return holidayService.getHolidays();
    }

    @PutMapping("/holiday/{id}")
    public ResponseEntity<HolidayEntity> updateHoliday(@PathVariable Long id, @RequestBody HolidayEntity holidayDetails) {
        HolidayEntity updatedHoliday = holidayService.updateHoliday(id, holidayDetails);
        return ResponseEntity.ok(updatedHoliday);
    }

    @DeleteMapping("/holiday/{id}")
    public void deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);


    }
}