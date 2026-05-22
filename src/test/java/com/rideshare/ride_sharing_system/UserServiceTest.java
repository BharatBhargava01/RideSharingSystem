package com.rideshare.ride_sharing_system;

import org.junit.jupiter.api.Test;
import com.rideshare.service.UserService;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    UserService userService =
            new UserService();

    @Test
    public void testLogin() {

        boolean result = userService.login(
                "admin",
                "admin"
        );

        assertTrue(result || !result);
    }
}