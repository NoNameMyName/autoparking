package org.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class auto_UI extends Application {
    @Override
    public void start(Stage main_UI) throws IOException {
        FXMLLoader root_UI = new FXMLLoader(getClass().getResource("/frontend/parking_UI.fxml"));
        Scene Scene = new Scene(root_UI.load(), 990, 450);
        main_UI.setResizable(false);
        main_UI.setTitle("Autoparking by KI-22-1");
        main_UI.setScene(Scene);
        main_UI.show();
    }

    public static void main(String[] args) {
        launch();
    }
}