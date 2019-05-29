package controllers;

import com.jfoenix.controls.JFXMasonryPane;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class GameController {

    @FXML
    private JFXMasonryPane masionary;

    @FXML
    private TextField test;

    private String A;

    @FXML
    void initialize() {

       TextField textField1 = creatField();
       textField1.setId("1");

       TextField textField2 = creatField();
       textField2.setId("2");

       masionary.getChildren().add(textField1);
       masionary.getChildren().add(textField2);

        textField1.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            saveText(event);
        });

        textField1.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            checkConditions(event);
        });
    }

    private TextField creatField() {

        TextField textField = new TextField("6");
        textField.setPrefWidth(30);
        textField.setPrefHeight(30);

        return textField;
    }

    private void saveText(Event event) {

        TextField textField = (TextField) event.getSource();
        A = textField.getText();
    }

    private void checkConditions(Event event) {

        TextField textField = (TextField) event.getSource();
        String s = textField.getText();

        String temp;
        if (hasLetter(s)) {
            if (Character.isDigit(s.charAt(0))) {
                temp = String.valueOf(s.charAt(0));
            } else if (Character.isDigit(s.charAt(s.length() - 1))) {
                temp = String.valueOf(s.charAt(s.length() - 1));
            } else {
                temp = "";
            }
        } else {

            if (s.length() >= 2) {
                String C;
                int i = s.indexOf(A);

                if (i == 0) {
                    C = s.substring(1);
                    temp = String.valueOf(C.charAt(0));
                } else {
                    C = s.substring(0, i);
                    temp = String.valueOf(C.charAt(0));
                }
            } else {
                temp = s;
            }
        }

        textField.setText(temp);
        test.setText(temp);
    }

    private boolean hasLetter(String s) {

        for (int i = 0; i < s.length(); i++) {
            if(Character.isLetter(s.charAt(i))) {
                return true;
            }
        }

        return false;
    }
}
