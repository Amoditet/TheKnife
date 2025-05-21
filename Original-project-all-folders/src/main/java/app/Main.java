package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Verifica se il file FXML esiste
        URL fxmlLocation = getClass().getResource("/view/home.fxml");
        if (fxmlLocation == null) {
            throw new RuntimeException("File FXML non trovato: /view/home.fxml");
        }

        // Carica il file FXML
        Parent root = FXMLLoader.load(fxmlLocation);
        root.getStyleClass().add("rounded-pane");

        Scene scene = new Scene(root);
        scene.setFill(null);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
