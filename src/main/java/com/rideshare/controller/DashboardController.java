package com.rideshare.controller;

import com.rideshare.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button adminButton;

    public void initialize() {
        String username = SessionManager.getCurrentUsername();
        String role = SessionManager.getCurrentRole();
        
        if (username != null) {
            welcomeLabel.setText("Welcome, " + username + "! (" + role + ")");
        } else {
            welcomeLabel.setText("Welcome! (" + (role != null ? role : "User") + ")");
        }

        if ("Admin".equalsIgnoreCase(role)) {
            adminButton.setVisible(true);
            adminButton.setManaged(true);
        } else {
            adminButton.setVisible(false);
            adminButton.setManaged(false);
        }
    }

    @FXML
    public void showOfferRide(ActionEvent event) {
        switchScene(event, "/fxml/OfferRide.fxml", "Ride Sharing - Offer a Ride");
    }

    @FXML
    public void showSearchRide(ActionEvent event) {
        switchScene(event, "/fxml/SearchRide.fxml", "Ride Sharing - Search Rides");
    }

    @FXML
    public void showBookRide(ActionEvent event) {
        switchScene(event, "/fxml/Booking.fxml", "Ride Sharing - Book a Ride");
    }

    @FXML
    public void showCheckBooking(ActionEvent event) {
        switchScene(event, "/fxml/CheckBooking.fxml", "Ride Sharing - Track Booking");
    }

    @FXML
    public void showAdminPanel(ActionEvent event) {
        switchScene(event, "/fxml/AdminPanel.fxml", "Ride Sharing - Administrative Panel");
    }

    @FXML
    public void logout(ActionEvent event) {
        SessionManager.setCurrentUserId(0);
        SessionManager.setCurrentUsername(null);
        SessionManager.setCurrentRole(null);
        switchScene(event, "/fxml/Login.fxml", "Ride Sharing - Login");
    }

    private void switchScene(ActionEvent event, String fxmlPath, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlPath));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
