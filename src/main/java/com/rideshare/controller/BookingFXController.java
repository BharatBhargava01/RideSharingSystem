package com.rideshare.controller;

import com.rideshare.service.BookingService;
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

public class BookingFXController {

    @FXML
    private TextField rideIdField;

    @FXML
    private TextField studentIdField;

    private final BookingService bookingService = new BookingService();

    public void initialize() {
        int studentId = SessionManager.getCurrentUserId();
        if (studentId != 0) {
            studentIdField.setText(String.valueOf(studentId));
        } else {
            studentIdField.setText("Not Logged In");
        }
    }

    @FXML
    public void bookRide(ActionEvent event) {
        String rideIdStr = rideIdField.getText().trim();
        int studentId = SessionManager.getCurrentUserId();

        if (rideIdStr.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ride ID is required.");
            return;
        }

        if (studentId == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "No logged-in user session found. Please login again.");
            return;
        }

        int rideId;
        try {
            rideId = Integer.parseInt(rideIdStr);
            if (rideId <= 0) {
                showAlert(Alert.AlertType.ERROR, "Error", "Ride ID must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ride ID must be a valid number.");
            return;
        }

        try {
            bookingService.bookRide(rideId, studentId);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Ride successfully booked! Have a great ride.");
            goBack(event);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Booking Failed", e.getMessage());
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
