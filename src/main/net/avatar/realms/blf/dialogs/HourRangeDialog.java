package net.avatar.realms.blf.dialogs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import net.avatar.realms.blf.models.Hour;

import java.io.IOException;

/**
 * Created by Nokorbis on 15/02/2016.
 */
public class HourRangeDialog extends Dialog<Pair<Hour, Hour>> {

    private Button doneButton;

    private ButtonType okButton;

    @FXML
    private ComboBox<Integer> startHoursCB;

    @FXML
    private ComboBox<Integer> startMinutesCB;

    @FXML
    private ComboBox<Integer> startSecondsCB;

    @FXML
    private ComboBox<Integer> endHoursCB;

    @FXML
    private ComboBox<Integer> endMinutesCB;

    @FXML
    private ComboBox<Integer> endSecondsCB;

    public HourRangeDialog() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/hoursdialog.fxml"));
        try {
            loader.setController(this);
            Node parent = loader.load();
            setGraphic(parent);
            initStyle(StageStyle.UTILITY);

            okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

            doneButton = (Button) getDialogPane().lookupButton(okButton);
            doneButton.setDefaultButton(true);

            //Fill comboboxes
            fill(startHoursCB, 24);
            fill(startMinutesCB, 60);
            fill(startSecondsCB,60);

            fill(endHoursCB, 24);
            fill(endMinutesCB, 60);
            fill(endSecondsCB, 60);

            startHoursCB.getSelectionModel().select(0);
            startMinutesCB.getSelectionModel().select(0);
            startSecondsCB.getSelectionModel().select(0);

            endHoursCB.getSelectionModel().select(23);
            endMinutesCB.getSelectionModel().select(59);
            endSecondsCB.getSelectionModel().select(59);

            setResultConverter(new HourConverter());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fill(ComboBox<Integer> cb, int max) {
        for (int i = 0; i < max; i++) {
            cb.getItems().add(i);
        }
    }


    private class HourConverter implements javafx.util.Callback<ButtonType, Pair<Hour, Hour>> {
        @Override
        public Pair<Hour, Hour> call(ButtonType param) {
            if (param != okButton) {
                return null;
            }

            Hour start = new Hour(startHoursCB.getValue(), startMinutesCB.getValue(), startSecondsCB.getValue());
            Hour end = new Hour(endHoursCB.getValue(), endMinutesCB.getValue(), endSecondsCB.getValue());
            Pair<Hour, Hour> result = new Pair<>(start, end);
            return result;
        }
    }
}
