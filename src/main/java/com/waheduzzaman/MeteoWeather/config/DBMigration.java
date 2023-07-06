package com.waheduzzaman.MeteoWeather.config;

import com.waheduzzaman.MeteoWeather.data.entity.User;
import com.waheduzzaman.MeteoWeather.data.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DBMigration {
    private final UserRepository userRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        if (!checkUserRecordExists()) {
            log.info("First run");
            log.info("Seeding initial data.....");
            seedInitialRecords();
        } else {
            log.info("Db already seeded");
        }
    }

    private void seedInitialRecords() {
        userRepository.save(new User("wahed", "USER", "123123123"));
        userRepository.save(new User("user", "USER", "123123123"));
    }

    private boolean checkUserRecordExists() {
        return userRepository.count() > 0;
    }
}
