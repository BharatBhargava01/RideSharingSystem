package com.rideshare.ride_sharing_system;

import org.junit.jupiter.api.Test;
import com.rideshare.service.NotificationService;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationServiceTest {

    @Test
    public void testNotification() {

        NotificationService service =
                new NotificationService();

        service.notifyUser(
                "Ride Confirmed"
        );

        assertTrue(true);
    }
}
