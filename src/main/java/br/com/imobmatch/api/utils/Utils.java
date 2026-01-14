package br.com.imobmatch.api.utils;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;

public class Utils {

    private static final int EXPIRATION_MINUTES = 10;
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String NUMBERS = "0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generateVerificationCode() {
        StringBuilder code = new StringBuilder(6);

        for (int i = 0; i < 3; i++) {
            code.append(NUMBERS.charAt(RANDOM.nextInt(NUMBERS.length())));
            code.append(LETTERS.charAt(RANDOM.nextInt(LETTERS.length())));
        }

        return code.toString();
    }

    public static boolean isCodeValid(LocalDateTime generatedAt) {
        Duration duration = Duration.between(generatedAt, LocalDateTime.now());
        return duration.toMinutes() < EXPIRATION_MINUTES;
    }

}
