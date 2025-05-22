package theknife.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import java.io.IOException;

import javafx.stage.Stage;

public class HomeController {

    private Stage primaryStage;

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private Button loginBtn;
    @FXML
    private Button registerBtn;

    @FXML
    private ImageView knifeImage;

    @FXML
    private StackPane rootPane;

    @FXML
    private Button exitBtn;

    @FXML
    public void initialize() {
        javafx.scene.effect.Blend blend = new javafx.scene.effect.Blend();
        blend.setMode(javafx.scene.effect.BlendMode.SRC_ATOP);

        javafx.geometry.Bounds bounds = knifeImage.getLayoutBounds();
        javafx.scene.effect.ColorInput redOverlay = new javafx.scene.effect.ColorInput(bounds.getMinX(),
                bounds.getMinY(), bounds.getWidth(), bounds.getHeight(), javafx.scene.paint.Color.RED);

        blend.setTopInput(redOverlay);
        knifeImage.setEffect(blend);

        if (exitBtn != null) {
            exitBtn.setOnAction(event -> handleExitAction());
        }

        // Ensure exitBtn is enabled and focusable
        exitBtn.setDisable(false);
        exitBtn.setFocusTraversable(true);

        // Add mouse event filter on rootPane to log mouse clicks if rootPane is not
        // null
        if (rootPane != null) {
            rootPane.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
                System.out.println("Mouse clicked at: " + event.getSceneX() + ", " + event.getSceneY());
            });
        } else {
            System.out.println("rootPane is null in initialize()");
        }

        // Explicitly bind exitBtn action to handleExitAction
        exitBtn.setOnAction(event -> handleExitAction());

        // Add mouse click event handler as a workaround
        exitBtn.setOnMouseClicked(event -> handleExitAction());

        // Add direct mouse click event handler to immediately exit as last resort
        exitBtn.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            System.out.println("Exit button direct mouse click handler invoked");
            System.exit(0);
        });
    };

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
    private Button ospiteBtn;

    @FXML
    private void handleExitAction() {
        System.out.println("Exit button clicked - handleExitAction invoked");
        // Close the current window explicitly
        primaryStage.close();
    }

    @FXML
    private void handleOspite() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
            Stage stage = (Stage) ospiteBtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
