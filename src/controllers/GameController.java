package controllers;

import classes.Sudoku;
import classes.TimerOfSudoku;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {

    @FXML
    private AnchorPane mainPane;

    @FXML
    private ToggleGroup numbersRadio;

    @FXML
    private JFXCheckBox checkBoxShowMisstakes;

    @FXML
    private Label labelTime;

    private TextField[][] fields = new TextField[9][9];
    private String A;

    private int SX = 0;
    private int FX = 3;

    private int SY = 0;
    private int FY = 3;

    private int[][] ints;
    private int[][] witoutGapInts;

    private TimerOfSudoku timer;
    private JFXRadioButton radioOne;
    private int counterRadio = 0;
    private int miss = 0;

    private SettingsController settingsController;

    @FXML
    void initialize() {

        settingsController = SettingsController.getInstance();

        Sudoku sudoku = new Sudoku();
        sudoku.setValues();
        checkLevelGame();

        ints = sudoku.getBoardWithGap(miss);
        witoutGapInts = sudoku.getBoard();

        methodsNeededToStart();
    }

    @FXML
    void checkMisstakes(ActionEvent event) {

        if (checkBoxShowMisstakes.isSelected()) {
            checkCorretions();
        } else {
            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 9; x++) {
                    if (!fields[y][x].getId().equals("computerNumber")){
                        fields[y][x].setId("emptyNumber");
                    }
                }
            }
        }

        makeBorder();
    }

    @FXML
    void changeViewNumber(ActionEvent event) {
        makeBorder();
    }

    @FXML
    void saveR(MouseEvent event) {
        radioOne = (JFXRadioButton) numbersRadio.getSelectedToggle();
    }

    @FXML
    void unSelR(MouseEvent event) {

        JFXRadioButton radioSecond = (JFXRadioButton) numbersRadio.getSelectedToggle();
        if (radioOne != null && radioOne == radioSecond) {
            radioOne.setSelected(false);
            hiddenBorder();
        }
    }

    @FXML
    void actionMenu(ActionEvent event) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("../xmls/MainMenu.fxml"));
            Scene scene = new Scene(root);

            Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void checkLevelGame() {

        miss = settingsController.getLevel();
    }

    private void makeBorder() {

        try {
            JFXRadioButton radio = (JFXRadioButton) numbersRadio.getSelectedToggle();
            String s = radio.getText();

            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 9; x++) {

                    if (fields[y][x].getText().equals(s)) {
                        fields[y][x].setStyle("-fx-border-color: #00ff99; -fx-border-radius: 12px; -fx-border-width: 3px; -fx-background-color: #00ff99");
                    } else {
                        fields[y][x].setStyle("-fx-border-color: white; -fx-border-radius: 12px; -fx-border-width: 2px");
                    }
                }
            }

        } catch (NullPointerException e) {
        }
    }

    private void methodsNeededToStart() {
        creatAllFields();
        putNumberstoFields();
        creatBoarder();
        setDisableOfRadioButtons();
        hiddenBorder();

        timer = new TimerOfSudoku(this);
        timer.starTimer();
    }

    private boolean checkIfWin() {

        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {

                try {
                    int temp = Integer.parseInt(fields[y][x].getText());

                    if (witoutGapInts[y][x] != temp) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    return false;
                }
            }
        }
        won();
        return true;
    }

    private void won() {
        System.out.println("You've won");
        timer.stopTimer();
    }

    private void setDisableOfRadioButtons() {

        for (int i = 0; i < 9; i++) {
            JFXRadioButton radio = (JFXRadioButton) numbersRadio.getToggles().get(i);

            int counter = 0;
            int number = Integer.parseInt(radio.getText());

            for(int y = 0; y < 9; y++) {
                for(int x = 0; x < 9; x++) {

                    try {
                        int temp = Integer.parseInt(fields[y][x].getText());

                        if(temp == number){
                            counter++;
                        }
                    } catch (NumberFormatException e) {

                    }
                }
            }

            if (counter >= 9) {
                radio.setDisable(true);
                radio.setSelected(false);  // Bład przy wylaczaniu powinno wyresetowac bordery we wszytkich kafelkach - zrobione
                hiddenBorder();
            } else {
                radio.setDisable(false);
            }
        }
    }

    private void hiddenBorder() {
        for(int y = 0; y < 9; y++) {
            for(int x = 0; x < 9; x++) {
                fields[y][x].setStyle("-fx-border-color: white; -fx-border-radius: 12px; -fx-border-width: 2px; ");
            }
        }
    }

    private void checkCorretions() {

        for (int y = 0; y <  9; y++) {
            for (int x = 0; x < 9; x++) {

                if (!fields[y][x].getId().equals("computerNumber") && !fields[y][x].getText().equals("")) {

                    int temp = 0;

                    try {
                        temp = Integer.parseInt(fields[y][x].getText());

                        if (witoutGapInts[y][x] != Integer.parseInt(fields[y][x].getText())) {
                            fields[y][x].setId("wrongNumber");
                        }
                    } catch (NumberFormatException e) {

                    }
                } else if (!fields[y][x].getId().equals("computerNumber") && fields[y][x].getText().equals("")) {
                    fields[y][x].setId("emptyNumber");
                }
            }
        }
    }

    private void creatBoarder() {

        mainPane.getChildren().add(creatPlane(0,0));
        mainPane.getChildren().add(creatPlane(124,0));
        mainPane.getChildren().add(creatPlane(248, 0));

        mainPane.getChildren().add(creatPlane(0,124));
        mainPane.getChildren().add(creatPlane(124,124));
        mainPane.getChildren().add(creatPlane(248,124));

        mainPane.getChildren().add(creatPlane(0,248));
        mainPane.getChildren().add(creatPlane(124,248));
        mainPane.getChildren().add(creatPlane(248,248));
    }

    private JFXMasonryPane creatPlane(int x, int y) {

        JFXMasonryPane example = new JFXMasonryPane();

        example.setLayoutX(215 + x);
        example.setLayoutY(16 + y);
        example.setCellWidth(35);
        example.setCellHeight(35);
        example.setPadding(new Insets(4, 4, 4, 4));
        example.setPrefWidth(125);
        example.setPrefHeight(125);
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
        textField.setPrefWidth(35);
        textField.setPrefHeight(35);
        textField.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            saveText(event);
        });
        textField.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            checkConditions(event);

            if (checkBoxShowMisstakes.isSelected()) {
                checkCorretions();
            }
            setDisableOfRadioButtons();
            makeBorder();
            checkIfWin();
        });

        return textField;
    }

    private void putNumberstoFields() {
        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {

                TextField field = fields[y][x];

                if (ints[y][x] == 0) {
                    field.setId("emptyNumber");
                    field.setText("");
                } else {
                    field.setText(String.valueOf(ints[y][x]));
                    field.setDisable(true);
                    field.setId("computerNumber");
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

    public void setLabelTime(String s) {
        labelTime.setText(s);
    }

    public void setMiss(int i) {
        this.miss = i;
    }
}