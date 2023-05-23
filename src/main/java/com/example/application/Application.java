package com.example.application;

import com.example.application.data.entity.AppUser;
import com.example.application.repositories.AppUserRepository;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.server.AppShellSettings;
import com.vaadin.flow.theme.Theme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * The entry point of the Spring Boot application.
 *
 * Use the @PWA annotation make the application installable on phones, tablets
 * and some desktop browsers.
 *
 */
@SpringBootApplication
@Theme(value = "weatherapplication")
public class Application implements AppShellConfigurator, CommandLineRunner {

    @Autowired
    AppUserRepository appUserRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if (appUserRepository.count() == 0L) {
            AppUser user = new AppUser();
            user.setEmail("admin@exe.com");
            user.setFirstName("admin");
            user.setLastName("admin");
            user.setPassword(passwordEncoder.encode("1234"));
            appUserRepository.save(user);
        }
    }

    @Override
    public void configurePage(AppShellSettings settings) {
        settings.addFavIcon("icon", "icons/icon.png", "16x16");
    }
}
