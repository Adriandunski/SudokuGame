package controllers;

import classes.Sudoku;
import com.jfoenix.controls.JFXMasonryPane;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class GameController {

    @FXML
    private AnchorPane mainPane;

    private TextField[][] fields = new TextField[9][9];
    private String A;

    private int SX = 0;
    private int FX = 3;

    private int SY = 0;
    private int FY = 3;

    private int[][] ints = {{1,1,1,2,2,2,5,5,5},
            {1,0,1,2,0,2,5,0,5},
            {1,1,1,2,2,2,5,5,5},
            {3,3,3,4,4,4,6,6,6},
            {3,0,3,4,0,4,6,0,6},
            {3,3,3,4,4,4,6,6,6},
            {1,1,1,2,2,2,5,5,5},
            {1,0,1,2,0,2,5,0,5},
            {1,1,1,2,2,2,5,5,5}};

    @FXML
    void initialize() {

//       TextField textField1 = creatField();
//       TextField textField2 = creatField();
//
//       masionary.getChildren().add(textField1);
//       masionary.getChildren().add(textField2);

        Sudoku sudoku = new Sudoku();
        sudoku.setValues();

        ints = sudoku.getBoard(20);


        creatAllFields();
        putNumberstoFields();
        creatBoarder();
    }

    private void creatBoarder() {

        mainPane.getChildren().add(creatPlane(0,0));
        mainPane.getChildren().add(creatPlane(109,0));
        mainPane.getChildren().add(creatPlane(218, 0));

        mainPane.getChildren().add(creatPlane(0,109));
        mainPane.getChildren().add(creatPlane(109,109));
        mainPane.getChildren().add(creatPlane(218,109));

        mainPane.getChildren().add(creatPlane(0,218));
        mainPane.getChildren().add(creatPlane(109,218));
        mainPane.getChildren().add(creatPlane(218,218));
    }

    private JFXMasonryPane creatPlane(int x, int y) {

        JFXMasonryPane example = new JFXMasonryPane();

        example.setLayoutX(180 + x);
        example.setLayoutY(40 + y);
        example.setCellWidth(30);
        example.setCellHeight(30);
        example.setPadding(new Insets(4, 4, 4, 4));
        example.setPrefWidth(110);
        example.setPrefHeight(110);
        example.setStyle("-fx-border-color: BLACK");
        creatBox(example);

        return example;
    }

    private void creatBox(JFXMasonryPane pane) {

        for (int x = SX; x < FX; x++) {
            for (int y = SY; y < FY; y++) {
                pane.getChildren().add(fields[x][y]);
            }
        }

        if (FY == 9) {
            SX += 3;
            FX += 3;

            SY = 0;
            FY = 3;
        } else {
            SY += 3;
            FY += 3;
        }
    }

    private void creatAllFields() {

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {

                fields[y][x] = creatField();
            }
        }
    }

    private TextField creatField() {

        TextField textField = new TextField("");
        textField.setPrefWidth(30);
        textField.setPrefHeight(30);
        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            saveText(event);
        });
        textField.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            checkConditions(event);
        });

        return textField;
    }

    private void putNumberstoFields() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {

                TextField field = fields[y][x];

                if (ints[y][x] == 0) {
                    field.setText("");
                } else {
                    field.setText(String.valueOf(ints[y][x]));
                }
            }
        }
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
