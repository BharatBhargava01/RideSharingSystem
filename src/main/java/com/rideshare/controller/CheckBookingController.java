package com.rideshare.controller;

import com.rideshare.database.RideDAO;
import com.rideshare.model.Booking;
import com.rideshare.model.Ride;
import com.rideshare.service.BookingService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CheckBookingController {

    @FXML
    private TextField confirmationIdField;

    @FXML
    private VBox resultsBox;

    @FXML
    private Label confirmIdLabel;

    @FXML
    private Label rideDetailsLabel;

    @FXML
    private Label rideIdLabel;

    @FXML
    private Label driverIdLabel;

    @FXML
    private Label studentIdLabel;

    @FXML
    private Label statusLabel;

    private final BookingService bookingService = new BookingService();
    private final RideDAO rideDAO = new RideDAO();

    @FXML
    public void checkBookingStatus(ActionEvent event) {
        String confirmationId = confirmationIdField.getText().trim();

        if (confirmationId.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Confirmation ID is required.");
            resultsBox.setVisible(false);
            resultsBox.setManaged(false);
            return;
        }

        try {
            Booking booking = bookingService.getBookingByConfirmationId(confirmationId);

            if (booking == null) {
                showAlert(Alert.AlertType.ERROR, "Not Found", "No booking record found for Confirmation ID: " + confirmationId);
                resultsBox.setVisible(false);
                resultsBox.setManaged(false);
                return;
            }

            // Booking is found, get associated ride details
            Ride ride = rideDAO.getRideById(booking.getRideId());

            confirmIdLabel.setText(booking.getConfirmationId());
            rideIdLabel.setText(String.valueOf(booking.getRideId()));
            studentIdLabel.setText(String.valueOf(booking.getStudentId()));
            statusLabel.setText(booking.getStatus());

            if (ride != null) {
                rideDetailsLabel.setText(ride.getSource() + " ➔ " + ride.getDestination());
                driverIdLabel.setText(String.valueOf(ride.getDriverId()));
            } else {
                rideDetailsLabel.setText("Ride details unavailable");
                driverIdLabel.setText("N/A");
            }

            resultsBox.setVisible(true);
            resultsBox.setManaged(true);

        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while tracking booking: " + e.getMessage());
            resultsBox.setVisible(false);
            resultsBox.setManaged(false);
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
