package theknife.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Label;
import java.nio.file.*;
import java.util.*;
import javafx.application.Platform;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @FXML
    private ImageView arrowImageView;

    @FXML
    private void initialize() {
        try {
            Image arrowImage = new Image(getClass().getResourceAsStream("/images/left-arrow.png"));
            arrowImageView.setImage(arrowImage);
            // Apply white color effect to arrow image
            ColorAdjust whiteEffect = new ColorAdjust();
            whiteEffect.setBrightness(1.0);
            arrowImageView.setEffect(whiteEffect);

        } catch (Exception e) {
            e.printStackTrace();
        }

        Scene scene = usernameField.getScene();
        if (scene != null) {
            scene.setFill(null);
        }
    }

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            errorLabel.setText("Inserisci username e password.");
            return;
        }

        username = username.trim();
        password = password.trim();

        try {
            List<String> lines = Files.readAllLines(Paths.get("data/utenti.csv"));
            for (String line : lines) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[2].trim().equals(username) && parts[3].trim().equals(password)) {
                    errorLabel.setText("Login riuscito!");
                    javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(
                            getClass().getResource("/view/dashboard.fxml"));
                    javafx.scene.Parent root = loader.load();
                    javafx.stage.Stage stage = (javafx.stage.Stage) usernameField.getScene().getWindow();
                    javafx.scene.Scene scene = new javafx.scene.Scene(root, 520, 450);
                    scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
                    stage.setScene(scene);
                    return;
                }
            }
            errorLabel.setText("Username o password errati.");
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Errore di accesso ai dati.");
        }
    }

    @FXML
    private void handleGoHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Scene scene = new Scene(root, 560, 540);
            // scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Errore durante la navigazione alla home.");
            errorLabel.setVisible(true);
        }
    }

    @FXML
    private void handleExitAction() {
        Platform.exit();
        System.exit(0);
    }

}
