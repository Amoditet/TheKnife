package theknife.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.application.Platform;

import java.nio.file.*;
import java.util.*;

public class RegisterController {

    @FXML
    private TextField nomeField;
    @FXML
    private TextField cognomeField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField domicilioField;
    @FXML
    private CheckBox clienteCheckBox;
    @FXML
    private CheckBox ristoratoreCheckBox;
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

        clienteCheckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                ristoratoreCheckBox.setSelected(false);
            }
        });
        ristoratoreCheckBox.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
            if (isNowSelected) {
                clienteCheckBox.setSelected(false);
            }
        });
    }

    @FXML
    private void handleRegister() {
        String nome = nomeField.getText();
        String cognome = cognomeField.getText();
        String username = usernameField.getText();
        String password = passwordField.getText();
        String domicilio = domicilioField.getText();

        nome = capitalizeFirstLetter(nome);
        cognome = capitalizeFirstLetter(cognome);
        domicilio = capitalizeFirstLetter(domicilio);

        String ruolo = null;
        if (clienteCheckBox.isSelected()) {
            ruolo = "cliente";
        } else if (ristoratoreCheckBox.isSelected()) {
            ruolo = "ristoratore";
        }

        if (nome.isEmpty() || cognome.isEmpty() || username.isEmpty() || password.isEmpty() || ruolo == null) {
            errorLabel.setText("Compila tutti i campi obbligatori.");
            errorLabel.setVisible(true);
            return;
        }

        String newUser = String.join(",", nome, cognome, username, password, domicilio, ruolo);

        try {
            Path path = Paths.get("data/utenti.csv");
            // Controlla se username già esiste
            if (Files.exists(path)) {
                List<String> lines = Files.readAllLines(path);
                for (String line : lines) {
                    String[] parts = line.split(",");
                    if (parts.length >= 3 && parts[2].equals(username)) {
                        errorLabel.setText("Username già esistente.");
                        errorLabel.setVisible(true);
                        return;
                    }
                }
            }

            String newUserWithNewline = System.lineSeparator() + newUser;
            Files.write(path, Arrays.asList(newUserWithNewline), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("Setting success: Registrazione completata!");
            errorLabel.setText("Registrazione completata!");
            errorLabel.setVisible(true);
            try {
                System.out.println("Loading dashboard.fxml");
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/dashboard.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) nomeField.getScene().getWindow();
                System.out.println("Stage obtained: " + stage);
                Scene scene = new Scene(root, 560, 540);
                stage.setScene(scene);
                stage.show();
                System.out.println("Navigation to dashboard.fxml successful");
            } catch (Exception ex) {
                ex.printStackTrace();
                errorLabel.setText("Errore durante la navigazione.");
                errorLabel.setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            errorLabel.setText("Errore durante la registrazione.");
            errorLabel.setVisible(true);
        }
    }

    private String capitalizeFirstLetter(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    @FXML
    private void handleGoHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/home.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nomeField.getScene().getWindow();
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
