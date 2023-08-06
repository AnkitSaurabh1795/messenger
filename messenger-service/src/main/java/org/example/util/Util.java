package org.example.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Util {
    private static final String EMPTY = "";

    private static final String HYPHEN = "-";

    private static final String USER = "UI";

    private static final String MESSAGE = "MSG";

    public static String generateUserId() {
        return generateId(USER);
    }

    public static String generateMessageId() {
        return generateId(MESSAGE);
    }

    private static String generateId(String prefix) {
        return prefix + UUID.randomUUID().toString().replace(HYPHEN, EMPTY);
    }
}
