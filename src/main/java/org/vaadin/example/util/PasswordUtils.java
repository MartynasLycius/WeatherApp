package org.vaadin.example.util;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class PasswordUtils {

    // Hash a password
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.withDefaults().hashToString(12, plainTextPassword.toCharArray());
    }

    // Verify a password
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(plainTextPassword.toCharArray(), hashedPassword);
        return result.verified;
    }
}
