package controllers;

import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class SettingsController {

    @FXML
    private ToggleGroup theKindOfGame;

    @FXML
    private ToggleGroup stepLevel;

    private static SettingsController instace;

    public SettingsController() {
        instace = this;
    }

    public static SettingsController getInstance() {
        return instace;
    }

    @FXML
    void startGame(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();

            Parent root = loader.load(getClass().getResource("../xmls/Game.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("xmls/styleGame.css");

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLevel() {

        JFXRadioButton radio = (JFXRadioButton) stepLevel.getSelectedToggle();

        if (radio.getText().equals("Easy")) {
            return 25;
        } else if (radio.getText().equals("Normal")) {
            return 45;
        } else {
            return 55;
        }
    }
}
