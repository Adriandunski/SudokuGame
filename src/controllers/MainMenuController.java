package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {

    @FXML
    void actionExit(ActionEvent event) {
        System.exit(-1);
    }

    @FXML
    void actionStartSudoku(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../xmls/Settings.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
