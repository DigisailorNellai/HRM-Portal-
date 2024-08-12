package com.example.HRM_Portal.service;

import com.example.HRM_Portal.entity.HolidayEntity;
import com.example.HRM_Portal.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public HolidayEntity updateHoliday(Long id, HolidayEntity holidayDetails) {
        Optional<HolidayEntity> optionalHoliday = holidayRepository.findById(id);
        if (optionalHoliday.isPresent()) {
            HolidayEntity holiday = optionalHoliday.get();
            holiday.setName(holidayDetails.getName());
            holiday.setDate(holidayDetails.getDate());
            holiday.setDay(holidayDetails.getDay());
            holiday.setDescription(holidayDetails.getDescription());
            return holidayRepository.save(holiday);
        } else {
            throw new RuntimeException("Holiday not found with id " + id);
        }
    }

    public void deleteHoliday(Long id) {
        holidayRepository.deleteById(id);
    }
}
