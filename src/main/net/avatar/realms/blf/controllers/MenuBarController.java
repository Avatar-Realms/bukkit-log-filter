package net.avatar.realms.blf.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import net.avatar.realms.blf.BukkitLogFilter;
import net.avatar.realms.blf.FiltersHandler;
import net.avatar.realms.blf.data.LogFileHandler;
import net.avatar.realms.blf.dialogs.CustomStringDialog;
import net.avatar.realms.blf.dialogs.HourRangeDialog;
import net.avatar.realms.blf.exceptions.BLFDataException;
import net.avatar.realms.blf.exceptions.BLFException;
import net.avatar.realms.blf.exceptions.BLFFormatException;
import net.avatar.realms.blf.models.CustomString;
import net.avatar.realms.blf.models.Hour;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class MenuBarController implements Controller{

    private MainController mainController;

    @FXML
    private MenuItem saveMenuItem;

    public void setMainController(MainController controller) {
        this.mainController = controller;
    }

    @FXML
    void aboutWindow(ActionEvent event) {

    }

    @FXML
    void changeHoursRange(ActionEvent event) {
        HourRangeDialog dialog = new HourRangeDialog();
        Optional<Pair<Hour, Hour>> result = dialog.showAndWait();
        if (result.isPresent()) {
            Pair<Hour, Hour> range = result.get();
            Hour start = range.getKey();
            Hour end = range.getValue();
            List<String> newLines = new LinkedList<>();

            for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
                try {
                    //[HH:MM:SS] -> 10 Characters
                    Hour lineHour = new Hour(line.substring(0, 10));
                    if (lineHour.isBefore(start)) {
                        continue;
                    }
                    if (lineHour.isAfter(end)) {
                        // Lets guess all next lines are after the end
                        break;
                    }
                    newLines.add(line);
                } catch (BLFFormatException e) {
                    //The line does not start by the hour, lets keep it
                    newLines.add(line);
                }
            }

            mainController.update(newLines);
        }
    }
    


    @FXML
    void closeWindow(ActionEvent event) {
        BukkitLogFilter.getLogFilter().stop();
    }

    @FXML
    void customKeepLines(ActionEvent event) {

        CustomStringDialog dialog = new CustomStringDialog("Keep line custom search");
        Optional<CustomString> result = dialog.showAndWait();
        if (result.isPresent()) {
            CustomString cs = result.get();
            Pattern pat = cs.compile();

            List<String> newLines = new LinkedList<>();

            for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
                if (FiltersHandler.matchesPattern(line, pat)) {
                    newLines.add(line);
                }
            }
            mainController.update(newLines);
        }
    }

    @FXML
    void customDeleteLines(ActionEvent event) {

        CustomStringDialog dialog = new CustomStringDialog("Delete line custom search");
        Optional<CustomString> result = dialog.showAndWait();
        if (result.isPresent()) {
            CustomString cs = result.get();
            Pattern pat = cs.compile();

            List<String> newLines = new LinkedList<>();

            for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
                if (!FiltersHandler.matchesPattern(line, pat)) {
                    newLines.add(line);
                }
            }
            mainController.update(newLines);
        }
    }

    @FXML
    void customDeleteStrings(ActionEvent event) {
        CustomStringDialog dialog = new CustomStringDialog("Delete string custom search");
        Optional<CustomString> result = dialog.showAndWait();
        if (result.isPresent()) {
            CustomString cs = result.get();
            Pattern pat = cs.compile();

            List<String> newLines = new LinkedList<>();

            for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
                newLines.add(pat.matcher(line).replaceAll(""));
            }
            mainController.update(newLines);
        }
    }

    @FXML
    void deleteCommandBlocksLines(ActionEvent event) {
        List<String> newLines = new LinkedList<>();

        for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
            if (!FiltersHandler.matchesCommandBlock(line)) {
                newLines.add(line);
            }
        }
        mainController.update(newLines);
    }

    @FXML
    void deleteConnectionsLines(ActionEvent event) {
        List<String> newLines = new LinkedList<>();

        for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
            if (!FiltersHandler.matchesConnection(line)) {
                newLines.add(line);
            }
        }
        mainController.update(newLines);
    }

    @FXML
    void deleteDisconnectionsLines(ActionEvent event) {
        List<String> newLines = new LinkedList<>();

        for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
            if (!FiltersHandler.matchesDisconnection(line)) {
                newLines.add(line);
            }
        }
        mainController.update(newLines);
    }

    @FXML
    void deleteDeathsLines(ActionEvent event) {
        List<String> newLines = new LinkedList<>();

        for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
            if (!FiltersHandler.matchesDeath(line)) {
                newLines.add(line);
            }
        }
        mainController.update(newLines);
    }

    @FXML
    void deleteExceptionsLines(ActionEvent event) {
        List<String> newLines = new LinkedList<>();

        for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
            if (!FiltersHandler.matchesException(line)) {
                newLines.add(line);
            }
        }
        mainController.update(newLines);
    }

    @FXML
    void deleteConsoleCommandLines(ActionEvent event) {
        List<String> newLines = new LinkedList<>();

        for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
            if (!FiltersHandler.matchesConsoleCommand(line)) {
                newLines.add(line);
            }
        }
        mainController.update(newLines);
    }

    @FXML
    void deletePlayerCommandLines(ActionEvent event) {
        List<String> newLines = new LinkedList<>();

        for (String line : BukkitLogFilter.getLogFilter().getLogs()) {
            if (!FiltersHandler.matchesPlayerCommand(line)) {
                newLines.add(line);
            }
        }
        mainController.update(newLines);
    }

    @FXML
    void openFile(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open log file");
        chooser.getExtensionFilters().addAll(LogFileHandler.LOG_EXTENSION, LogFileHandler.TEXT_EXTENSION);
        File selectedFile = chooser.showOpenDialog(BukkitLogFilter.getLogFilter().getStage());
        if (selectedFile != null) {
            try {
                List<String> lines = LogFileHandler.readFile(selectedFile);
                mainController.update(lines);
                updateSaveMenuItem();
            } catch (BLFDataException e) {
                handleException(e);
            }
        }
    }

    @FXML
    void saveResult(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save log file");
        chooser.getExtensionFilters().addAll(LogFileHandler.LOG_EXTENSION);
        File selectedFile = chooser.showSaveDialog(BukkitLogFilter.getLogFilter().getStage());
        if (selectedFile != null) {
            try {
                LogFileHandler.writeFile(selectedFile, BukkitLogFilter.getLogFilter().getLogs());
            } catch (BLFDataException e) {
                handleException(e);
            }
        }
    }

    @FXML
    void initialize() {

    }

    private void updateSaveMenuItem() {
        List logs = BukkitLogFilter.getLogFilter().getLogs();
        if (logs != null && !logs.isEmpty()) {
            saveMenuItem.setDisable(false);
        }
        else {
            saveMenuItem.setDisable(true);
        }
    }

    private void handleException (BLFException exception) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An exception has been thrown");
        alert.setContentText(exception.getMessage());

        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.getCause().printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("The exception stacktrace was:");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }
}
