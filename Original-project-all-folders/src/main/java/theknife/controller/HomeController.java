package theknife.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class HomeController {

    @FXML
    private Button loginBtn;
    @FXML
    private Button registerBtn;

    @FXML
    private ImageView knifeImage;

    @FXML
    public void initialize() {
        javafx.scene.effect.Blend blend = new javafx.scene.effect.Blend();
        blend.setMode(javafx.scene.effect.BlendMode.SRC_ATOP);

        javafx.geometry.Bounds bounds = knifeImage.getLayoutBounds();
        javafx.scene.effect.ColorInput redOverlay = new javafx.scene.effect.ColorInput(bounds.getMinX(),
                bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), javafx.scene.paint.Color.RED);

        blend.setTopInput(redOverlay);
        knifeImage.setEffect(blend);
    }

    @FXML
    private void handleLogin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Stage stage = (Stage) loginBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/register.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) registerBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExitAction() {
        Platform.exit();
        System.exit(0);
    }
}
