package com.hotel.booking.api.automation.utils;

import java.util.concurrent.ThreadLocalRandom;

public class CommonUtils {
    public static int generateRandomRoomId() {
        return ThreadLocalRandom.current().nextInt(100, 1000);
    }
}

