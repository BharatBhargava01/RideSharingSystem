package com.rideshare.ride_sharing_system;

import com.rideshare.database.DBConnection;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

public class DBConnectionTest {

    @Test
    public void testConnection() {

        Connection con = DBConnection.getConnection();

        assertNotNull(con);
    }
}
