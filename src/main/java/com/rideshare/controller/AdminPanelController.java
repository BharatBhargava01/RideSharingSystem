package com.rideshare.controller;

import com.rideshare.database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AdminPanelController {

    @FXML
    private ListView<String> adminListView;

    @FXML
    public void viewUsers(ActionEvent event) {
        ObservableList<String> userList = FXCollections.observableArrayList();
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to connect to the database.");
                return;
            }

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT user_id, name, email, phone, role FROM users ORDER BY user_id");

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");

                userList.add("User ID: " + id + " | " + name + " (" + role + ") | Email: " + email + " | Phone: " + phone);
            }

            adminListView.setItems(userList);
            if (userList.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Records", "No user records found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load users: " + e.getMessage());
        }
    }

    @FXML
    public void viewRides(ActionEvent event) {
        ObservableList<String> rideList = FXCollections.observableArrayList();
        try {
            Connection con = DBConnection.getConnection();
            if (con == null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to connect to the database.");
                return;
            }

            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT ride_id, driver_id, source, destination, seats FROM rides ORDER BY ride_id");

            while (rs.next()) {
                int id = rs.getInt("ride_id");
                int driverId = rs.getInt("driver_id");
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                int seats = rs.getInt("seats");

                rideList.add("Ride ID: " + id + " | Driver ID: " + driverId + " | " + source + " ➔ " + destination + " | Seats Left: " + seats);
            }

            adminListView.setItems(rideList);
            if (rideList.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Records", "No offered rides found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load rides: " + e.getMessage());
        }
    }

    @FXML
    public void goBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Ride Sharing - Dashboard");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }
}
