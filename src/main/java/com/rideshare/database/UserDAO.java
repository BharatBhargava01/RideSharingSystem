package com.rideshare.database;

import java.sql.*;
import com.rideshare.model.User;

public class UserDAO {

    public boolean validateUser(String username,
                                String password) {

        try {

            Connection con = DBConnection.getConnection();

            String query =
                    "SELECT * FROM users WHERE name=? AND password=?";

            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                com.rideshare.utils.SessionManager.setCurrentUserId(rs.getInt("user_id"));
                com.rideshare.utils.SessionManager.setCurrentUsername(rs.getString("name"));
                com.rideshare.utils.SessionManager.setCurrentRole(rs.getString("role"));
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean createUser(User user) {
        try {
            Connection con = DBConnection.getConnection();
            String query = "INSERT INTO users (name, email, phone, password, role) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getPassword());
            
            String role = "User";
            if (user instanceof com.rideshare.model.Driver) {
                role = "Driver";
            } else if (user instanceof com.rideshare.model.Student) {
                role = "Student";
            } else if (user instanceof com.rideshare.model.Admin) {
                role = "Admin";
            }
            ps.setString(5, role);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}