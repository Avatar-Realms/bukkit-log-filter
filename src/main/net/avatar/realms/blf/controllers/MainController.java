package net.avatar.realms.blf.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import net.avatar.realms.blf.BukkitLogFilter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by Nokorbis on 15/02/2016.
 */
public class MainController implements Controller {

    @FXML
    private MenuBarController menubarController;

    @FXML
    private ListView<String> linesLV;

    @FXML
    private Label lineAmountL;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private BorderPane borderPane;

    @FXML
    void initialize() {
        assert borderPane != null : "fx:id=\"borderPane\" was not injected: check your FXML file 'main.fxml'.";
        assert menubarController != null : "fx:id=\"menubarController\" was not injected: check your FXML file 'main.fxml'.";
        menubarController.setMainController(this);
    }

    public void update (List<String> logs) {
        BukkitLogFilter.getLogFilter().setLogs(logs);
        linesLV.getItems().clear();
        if (logs != null) {
            lineAmountL.setText(String.valueOf(logs.size()));
            for (String line : logs) {
                linesLV.getItems().add(line);
            }
        }
        else {
            lineAmountL.setText("0");
        }
    }
}
