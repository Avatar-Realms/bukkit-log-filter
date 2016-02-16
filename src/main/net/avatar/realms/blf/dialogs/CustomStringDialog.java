package net.avatar.realms.blf.dialogs;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.StageStyle;
import net.avatar.realms.blf.FiltersHandler;
import net.avatar.realms.blf.models.CustomString;

import java.io.IOException;

/**
 * Created by Nokorbis on 16/02/2016.
 */
public class CustomStringDialog extends Dialog<CustomString> {

    private static String lastStringEntered = null;
    private static Boolean lastRegex = null;
    private static Boolean lastCase = null;

    private Button doneButton;

    private ButtonType okButton;

    @FXML
    private ComboBox<String> firstCB;

    @FXML
    private ComboBox<String> secondCB;

    @FXML
    private TextField thirdTF;

    @FXML
    private CheckBox regexCB;

    @FXML
    private CheckBox caseCB;

    @FXML
    private CheckBox saveCB;

    @FXML
    private TextField nameTF;

    public CustomStringDialog(String title) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/customdialog.fxml"));
        try {
            loader.setController(this);
            Node parent = loader.load();
            setGraphic(parent);
            initStyle(StageStyle.UTILITY);
            this.setTitle(title);

            okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

            doneButton = (Button) getDialogPane().lookupButton(okButton);
            doneButton.setDefaultButton(true);

            //Fill comboboxes
            firstCB.getItems().addAll("", FiltersHandler.HOUR_REGEX);
            secondCB.getItems().addAll("", FiltersHandler.THREAD_INFO, FiltersHandler.S_THREAD_WARN, FiltersHandler.THREAD_WARN, FiltersHandler.CRAFT_WARN, FiltersHandler.CRAFT_INFO);

            firstCB.getSelectionModel().select(0);
            secondCB.getSelectionModel().select(0);

            if (lastStringEntered != null) {
                thirdTF.setText(lastStringEntered);
            }

            if (lastCase != null) {
                caseCB.setSelected(lastCase);
            }

            if (lastRegex != null) {
                regexCB.setSelected(lastRegex);
            }

            setResultConverter(new CustomStringConverter());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class CustomStringConverter implements javafx.util.Callback<ButtonType, CustomString> {
        @Override
        public CustomString call(ButtonType param) {
            if (param != okButton) {
                return null;
            }
            lastStringEntered = thirdTF.getText();
            lastCase = caseCB.isSelected();
            lastRegex = regexCB.isSelected();
            CustomString cs = new CustomString(firstCB.getSelectionModel().getSelectedItem(),
                    secondCB.getSelectionModel().getSelectedItem(),
                    thirdTF.getText(),
                    regexCB.isSelected(),
                    caseCB.isSelected(),
                    saveCB.isSelected() ? nameTF.getText() : null);
            return cs;
        }
    }
}
