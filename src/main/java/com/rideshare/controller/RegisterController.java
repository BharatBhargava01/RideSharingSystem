package com.rideshare.controller;

import com.rideshare.model.Admin;
import com.rideshare.model.Driver;
import com.rideshare.model.Student;
import com.rideshare.model.User;
import com.rideshare.service.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private ComboBox<String> roleComboBox;

    private UserService userService = new UserService();

    @FXML
    public void initialize() {
        roleComboBox.setItems(FXCollections.observableArrayList(
                "Student", "Driver", "Admin"
        ));
        roleComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    public void register() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String role = roleComboBox.getValue();

        // Validation
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()
                || password.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "All fields are required.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Passwords do not match.");
            return;
        }

        // Create user based on role
        User user;
        switch (role) {
            case "Driver":
                user = new Driver(0, name, email, phone, password, "");
                break;
            case "Admin":
                user = new Admin(0, name, email, phone, password);
                break;
            case "Student":
            default:
                user = new Student(0, name, email, phone, password, "");
                break;
        }

        boolean success = userService.register(user);

        if (success) {
            showAlert(Alert.AlertType.INFORMATION,
                    "Registration successful! You can now login.");
            goToLogin();
        } else {
            showAlert(Alert.AlertType.ERROR,
                    "Registration failed. Please try again.");
        }
    }

    @FXML
    public void goToLogin() {
        try {
            Parent root = FXMLLoader.load(
                    getClass().getResource("/fxml/Login.fxml")
            );
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Ride Sharing - Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
    }
}
