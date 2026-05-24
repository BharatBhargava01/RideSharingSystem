package com.rideshare.controller;

import com.rideshare.model.Ride;
import com.rideshare.service.BookingService;
import com.rideshare.service.MatchingEngine;
import com.rideshare.service.RideService;
import com.rideshare.utils.SessionManager;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class SearchRideController {

    @FXML
    private TextField sourceField;

    @FXML
    private TextField destinationField;

    @FXML
    private ListView<String> resultsListView;

    private final RideService rideService = new RideService();
    private final BookingService bookingService = new BookingService();
    private final MatchingEngine matchingEngine = new MatchingEngine();

    @FXML
    public void search(ActionEvent event) {
        String source = sourceField.getText().trim();
        String destination = destinationField.getText().trim();

        if (source.isEmpty() || destination.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Both source and destination are required.");
            return;
        }

        try {
            List<Ride> allRides = rideService.getRides();
            List<Ride> matchedRides = matchingEngine.searchRide(allRides, source, destination);

            ObservableList<String> listItems = FXCollections.observableArrayList();
            for (Ride ride : matchedRides) {
                listItems.add("Ride ID: " + ride.getRideId() + " | Driver ID: " + ride.getDriverId() + " | " + ride.getSource() + " ➔ " + ride.getDestination() + " | Seats Left: " + ride.getSeats());
            }

            resultsListView.setItems(listItems);
            if (matchedRides.isEmpty()) {
                showAlert(Alert.AlertType.INFORMATION, "Results", "No matching rides found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to query database: " + e.getMessage());
        }
    }

    @FXML
    public void bookSelectedRide(ActionEvent event) {
        String selectedItem = resultsListView.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a ride from the list first.");
            return;
        }

        // Parse Ride ID from string e.g. "Ride ID: 5 | ..."
        int rideId = -1;
        try {
            String temp = selectedItem.substring(selectedItem.indexOf("Ride ID:") + 8).trim();
            String rideIdStr = temp.substring(0, temp.indexOf("|")).trim();
            rideId = Integer.parseInt(rideIdStr);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Could not parse selected Ride ID.");
            return;
        }

        int studentId = SessionManager.getCurrentUserId();
        if (studentId == 0) {
            showAlert(Alert.AlertType.ERROR, "Error", "No active login session. Please login again.");
            return;
        }

        try {
            com.rideshare.model.Booking booking = bookingService.bookRide(rideId, studentId);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Ride successfully booked!\n\nYour Confirmation ID is: " + booking.getConfirmationId() + "\n\nUse this ID on the 'Check Booking' screen to track your ride status.");
            // Refresh search results
            search(event);
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
