package com.rideshare.controller;

import com.rideshare.model.Ride;
import com.rideshare.service.RideService;
import com.rideshare.utils.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OfferRideController {

    @FXML
    private TextField sourceField;

    @FXML
    private TextField destinationField;

    @FXML
    private TextField seatsField;

    private final RideService rideService = new RideService();

    @FXML
    public void offerRide(ActionEvent event) {
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();
        String seatsStr = seatsField.getText().trim();

        if (source.isEmpty() || destination.isEmpty() || seatsStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "All fields are required.");
            return;
        }

        int seats;
        try {
            seats = Integer.parseInt(seatsStr);
            if (seats <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Seats must be greater than zero.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Seats must be a valid number.");
            return;
        }

        int driverId = SessionManager.getCurrentUserId();
        if (driverId == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "No logged-in user session found. Please login again.");
            return;
        }

        try {
            Ride ridePlaceholder = new Ride(0, driverId, source, destination, seats);
            rideService.addRide((long) driverId, ridePlaceholder);
            
            showAlert(Alert.AlertType.INFORMATION, "Success", "Ride offered successfully!");
            goBack(event);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to offer ride in database: " + e.getMessage());
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
