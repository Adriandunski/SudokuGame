package classes;

import java.security.SecureRandom;
import java.util.*;

public class Sudoku {

    int numbers[][];

    public Sudoku() {
        numbers = new int[9][9];
    }

    public void setValues() {
        initialSquares();
        initialRestSquare();
    }

    private void initialSquares() {

        for (int y = 0; y < 9; y += 3) {
            fillBox(y, y);
        }
    }

    public void initialRestSquare() {

        long timeStart = System.currentTimeMillis();
        do {
            clearMap();

            for(int y  = 0; y < 9; y += 3) {
                for (int x = 0; x < 9; x += 3) {
                    if(!isFullSquare(y,x)) {
                        fillRestBox(y,x);
                    }
                }
            }
        } while (!isFullMap()); {
            printValues();
        }
    }

    private boolean fillRestBox(int startY, int startX) {

        long nowMils = System.currentTimeMillis();
        List<Integer> integers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        List<Integer> wrote = new ArrayList<>();

        Set<Integer> set = new HashSet<>();
        int num = 0;

        // We try complet all field in square.
        do {

            // Fist you check every single field if is correct
            for (int y = startY; y < startY + 3; y++) {
                for (int x = startX; x < startX + 3; x++) {

                    // But we want write optional number only to field which hasn't got number
                    if (numbers[y][x] == 0) {
                        // This do/while check conditions until are good if not we try swap numbers between yourself
                        do {
                            // Here we try swap numbers between youself if we haven't got other possibilities with
                            // numbers which are not written.
                            if (set.size() == integers.size()) {

                                int counter = 0; // this counter tell us that we cannot also use numbers which are written
                                Task1:
                                for (int j = startY; j < startY + 3; j++) {
                                    for (int i = startX; i < startX + 3; i++) {
                                        // Here we want check numbers, but not zero;
                                        if (numbers[j][i] != 0) {
                                            // We check now every written number in square to index with we have problem,
                                            // if we can swap we do it, but for checking we must change number to zero, because
                                            // we want check columns and rows but without number in our square if we cannot
                                            // swap, at the end number is coming back to the same place;

                                            Integer temp = new Integer(numbers[j][i]);
                                            numbers[j][i] = 0;

                                            if (checkRowAndCol(y,x, temp)) {

                                                num = temp;
                                                integers.add(temp);
                                                wrote.remove(temp);
                                                set.clear();
                                                break Task1;
                                            } else {
                                                numbers[j][i] = temp;
                                                counter++;
                                            }
                                        }
                                        // this 'if' improve the time :)
                                        if (counter == wrote.size() && set.size() == integers.size()) {
                                            return false;
                                        }
                                    }
                                }
                            }

                            // I know this solve isn't good but works :) if appers situation that we cannot complet square
                            // we get infinity loops, this 'if' solves this;
                            long longMills2 = System.currentTimeMillis();
                            if (longMills2 - nowMils > 2) {
                                return false;
                            }

                            // We choose one number from unselected yet numbers then, we shuffle array, we don't want
                            // always choose the same number, and finnaly we add number to SetCollection this number also.
                            // This set tell us when we used all numbers, so we will know that we cannot use numbers which are
                            // not written (integers) so we must check numbers which we've written(wrote). Simply we want swap numbers between yourself;
                            if (integers.size() > 0) {
                                Collections.shuffle(integers);
                                num = integers.stream().findFirst().get().intValue();
                                set.add(num);
                            }

                        // We add number to the right index than we add that number to 'wrote' and delate from 'integers'
                        // we must also clear our 'set' because next filed has new index so we want check again all possibilities;
                        } while (!checkRowAndCol(y,x,num)); {

                            numbers[y][x] = num;

                            Integer newI = new Integer(num);
                            integers.remove(newI);
                            wrote.add(newI);
                            set.clear();
                        }
                    }
                }
            }
        } while (!isFullSquare(startY, startX)); {
            return true;
        }
    }

    //Method check if all fields in box are completed.
    private boolean isFullSquare(int startY, int startX) {

        for(int y = startY; y < startY + 3; y++) {
            for (int x = startX; x < startX + 3; x++) {
                if (numbers[y][x] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // Method check if all boxes are completed
    private boolean isFullMap() {

        for(int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                if (numbers[y][x] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    private void clearMap() {

        clearBox(0, 3);
        clearBox(0, 6);
        clearBox(3, 0);
        clearBox(3, 6);
        clearBox(6, 0);
        clearBox(6, 3);
    }

    private void clearBox(int startY, int startX) {

        for (int y = startY; y < startY + 3; y++) {
            for (int x = startX; x < startX + 3; x++) {
                numbers[y][x] = 0;
            }
        }
    }

    // This method we use to fill tree boxes
    private void fillBox(int startY, int startX) {

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {

                int num;

                do {
                    num = getGenerateNum(9);
                } while (!checkBox(startY, startX, num)); {
                    numbers[startY + y][startX + x] = num;
                }
            }
        }
    }

    // Method check if we have more than single number in our box
    private boolean checkBox(int startY, int startX, int number) {

        for(int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {

                if(numbers[startY + y][startX + x] == number) {
                    return false;
                }
            }
        }
        return true;
    }

    // Method check Column and Row, if there is the same number which we are looking.
    public boolean checkRowAndCol(int Y, int X, int number) {

        if(!checkRow(Y, number)) {
            return false;
        }

        if (!checkCol(X, number)) {
            return false;
        }
        return true;
    }

    private boolean checkRow(int Y, int number) {

        for (int w = 0; w < 9; w++) {
            if (numbers[Y][w] == number) {
                return false;
            }
        }
        return true;
    }

    private boolean checkCol(int X, int number) {

        for (int w = 0; w < 9; w++) {
            if (numbers[w][X] == number) {
                return false;
            }
        }
        return true;
    }

    // Method generates number from 1 to 9
    private int getGenerateNum(int x) {

        SecureRandom random = new SecureRandom();
        return random.nextInt(x) + 1;
    }

    // Method print array of number;
    public void printValues() {

        for (int y = 0; y < 9; y++) {
            for (int x = 0; x < 9; x++) {
                System.out.print(numbers[y][x] + " ");
            }
            System.out.println();
        }
    }
}