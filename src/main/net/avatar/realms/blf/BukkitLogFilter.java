package net.avatar.realms.blf;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.List;
import java.util.Optional;

public class BukkitLogFilter extends Application {

    private static BukkitLogFilter logFilter;

    private Stage stage;

    private List<String> logs;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/views/main.fxml"));

        primaryStage.setTitle("Bukkit Log Filter");
        primaryStage.setScene(new Scene(root));
        primaryStage.setOnCloseRequest((e) -> stop());
        primaryStage.show();

        logFilter = this;
        stage = primaryStage;
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void stop() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Close Bukkit Log Filter");
        alert.setHeaderText("Are you sure you want to close Bukkit Log Filter ?");
        alert.setContentText("Unsaved changes will be lost.");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            Platform.exit();
            System.exit(0);
        }
    }

    public Stage getStage() {
        return stage;
    }

    public static BukkitLogFilter getLogFilter() {
        return logFilter;
    }

    public void setLogs(List<String> lines) {
        this.logs = lines;
    }

    public List<String> getLogs() {
        return this.logs;
    }
}
