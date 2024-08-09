package com.example.HRM_Portal.config;


import com.example.HRM_Portal.entity.HolidayEntity;
import com.example.HRM_Portal.repository.HolidayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Configuration
public class  HolidayConfig {

    @Autowired
    private HolidayRepository holidayRepository;

    @Bean
    CommandLineRunner commandLineRunner() {
        return args -> {
            HolidayEntity newYear = new HolidayEntity(null, "New Year", LocalDate.of(2024, 1, 1), "Monday", "Celebration of the New Year");
            HolidayEntity republicDay = new HolidayEntity(null, "Republic Day", LocalDate.of(2024, 1, 26), "Friday", "National holiday in India");
            HolidayEntity independenceDay = new HolidayEntity(null, "Independence Day", LocalDate.of(2024, 8, 15), "Thursday", "Celebration of independence");
            HolidayEntity christmas = new HolidayEntity(null, "Christmas", LocalDate.of(2024, 12, 25), "Wednesday", "Celebration of Christmas");
            HolidayEntity pongal =new HolidayEntity(null, "Pongal", LocalDate.of(2024, 1, 14), "Monday", "Pongal Festival");
            HolidayEntity thiruvalluvarDay=new HolidayEntity(null, "Thiruvalluvar Day", LocalDate.of(2024, 1, 15), "Tuesday", "Thiruvalluvar Day");
            HolidayEntity uzhavarThirunal=new HolidayEntity(null, "Uzhavar Thirunal", LocalDate.of(2024, 1, 16), "Wednesday", "Uzhavar Thirunal");
            HolidayEntity tamilNewYear=new HolidayEntity(null, "Tamil New Year", LocalDate.of(2024, 4, 14), "Sunday", "Tamil New Year");
            HolidayEntity laboursDay=new HolidayEntity(null, "Labour's Day", LocalDate.of(2024, 5, 1), "Wednesday", "Labour's Day");
            HolidayEntity gandhiJayanthi=new HolidayEntity(null, "Gandhi Jayanthi ", LocalDate.of(2024, 10, 2), "Wednesday", "Gandhi Jayanthi");

            List<HolidayEntity> holidays = Arrays.asList(newYear, republicDay, independenceDay, christmas,pongal,thiruvalluvarDay,uzhavarThirunal,tamilNewYear,laboursDay,gandhiJayanthi);

            holidayRepository.saveAll(holidays);
   };
 }
}