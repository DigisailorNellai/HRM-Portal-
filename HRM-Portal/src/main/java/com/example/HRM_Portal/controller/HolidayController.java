package com.example.HRM_Portal.controller;

import com.example.HRM_Portal.entity.HolidayEntity;
import com.example.HRM_Portal.service.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

        import java.util.List;

@RestController
@RequestMapping("/api/holidays")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @PostMapping
    public HolidayEntity createHoliday(@RequestBody HolidayEntity holiday) {
        return holidayService.saveHoliday(holiday);
    }

    @PostMapping("/bulk")
    public List<HolidayEntity> createHolidays(@RequestBody List<HolidayEntity> holidays) {
        return holidayService.saveHolidays(holidays);
    }

    @GetMapping
    public List<HolidayEntity> getHolidays() {
        return holidayService.getHolidays();
    }

    @DeleteMapping("/{id}")
    public void deleteHoliday(@PathVariable Long id) {
        holidayService.deleteHoliday(id);


 }
}