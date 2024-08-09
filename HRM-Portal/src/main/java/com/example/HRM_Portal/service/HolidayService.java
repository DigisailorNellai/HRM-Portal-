package com.example.HRM_Portal.service;

import com.example.HRM_Portal.entity.HolidayEntity;
import com.example.HRM_Portal.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class HolidayService {

    @Autowired
    private HolidayRepository holidayRepository;

    public List<HolidayEntity> saveHolidays(List<HolidayEntity> holidays) {
        return holidayRepository.saveAll(holidays);
    }

    public HolidayEntity saveHoliday(HolidayEntity holiday) {
        return holidayRepository.save(holiday);
    }

    public List<HolidayEntity> getHolidays() {
        return holidayRepository.findAll();
    }

    public void deleteHoliday(Long id) {
        holidayRepository.deleteById(id);
 }


}