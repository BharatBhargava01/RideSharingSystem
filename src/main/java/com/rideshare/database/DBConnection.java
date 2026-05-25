package com.rideshare.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = System.getenv("DB_URL");
    private static final String USER = System.getenv("DB_USERNAME");
    private static final String PASSWORD = System.getenv("DB_PASSWORD");

    static {
        initializeDatabase();
    }

    private static void initializeDatabase() {
        try (Connection con = getConnection()) {
            if (con != null) {
                try (java.sql.Statement stmt = con.createStatement()) {
                    stmt.execute("ALTER TABLE bookings ADD COLUMN IF NOT EXISTS confirmation_id VARCHAR(50) UNIQUE;");
                    System.out.println("Database auto-migrated successfully (added confirmation_id column if not exists).");
                }
            }
        } catch (Exception e) {
            System.err.println("Database auto-migration failed: " + e.getMessage());
        }
    }

    public static Connection getConnection() {

        Connection con = null;

        try {
            Class.forName("org.postgresql.Driver");

            con = DriverManager.getConnection(
                    URL,
                    USER,
                    PASSWORD);

            System.out.println("Database Connected");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return con;
    }
}