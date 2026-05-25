package com.rideshare.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DBConnection {

    private static final Map<String, String> dotEnv = loadDotEnv();
    
    private static final String URL = getEnvValue("DB_URL", "jdbc:postgresql://localhost:5432/rideshare");
    private static final String USER = getEnvValue("DB_USERNAME", "postgres");
    private static final String PASSWORD = getEnvValue("DB_PASSWORD", "");

    private static Map<String, String> loadDotEnv() {
        Map<String, String> envMap = new HashMap<>();
        File envFile = new File(".env");
        if (envFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(envFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("#")) {
                        continue;
                    }
                    int eqIdx = line.indexOf('=');
                    if (eqIdx > 0) {
                        String key = line.substring(0, eqIdx).trim();
                        String value = line.substring(eqIdx + 1).trim();
                        if ((value.startsWith("\"") && value.endsWith("\"")) ||
                            (value.startsWith("'") && value.endsWith("'"))) {
                            value = value.substring(1, value.length() - 1);
                        }
                        envMap.put(key, value);
                    }
                }
            } catch (Exception e) {
                System.err.println("Could not read .env file: " + e.getMessage());
            }
        }
        return envMap;
    }

    private static String getEnvValue(String name, String defaultValue) {
        String val = System.getenv(name);
        if (val != null && !val.trim().isEmpty()) {
            return val.trim();
        }
        val = dotEnv.get(name);
        if (val != null && !val.trim().isEmpty()) {
            return val.trim();
        }
        return defaultValue;
    }

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