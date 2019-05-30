package classes;

import controllers.GameController;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

public class TimerOfSudoku {

    private final GameController instance;
    private Timer timer;
    private int seconds = 0;

    public TimerOfSudoku(GameController instance) {
        this.instance = instance;
        this.timer = new Timer();
    }

    public void starTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                seconds++;
                Platform.runLater(() -> {
                    setTimeInLabelOfGame(makeTimeToString());
                });
            }
        }, 1000, 1000);
    }

    public void stopTimer() {
        timer.cancel();
    }

    private String makeTimeToString() {

        int sec = seconds;

        int hours;
        int minutes;
        int seconds;

        if (sec >= 3600) {

            seconds = sec % 60;
            int temp = sec / 60;
            minutes = temp % 60;
            hours = temp / 60;

        } else if (sec >= 60) {

            hours = 0;
            minutes = sec / 60;
            seconds = sec % 60;

        } else {
            hours = 0;
            minutes = 0;
            seconds = sec;
        }

        String sHours;
        String sMinutes;
        String sSeconds;

        if (seconds <= 9) {
            sSeconds = String.format("0%d", seconds);
        } else {
            sSeconds = String.valueOf(seconds);
        }

        if (minutes <= 9) {
            sMinutes = String.format("0%d", minutes);
        } else {
            sMinutes = String.valueOf(minutes);
        }

        if (hours <= 9) {
            sHours = String.format("0%d", hours);
        } else {
            sHours = String.valueOf(hours);
        }

        return String.format("%s:%s:%s",sHours ,sMinutes ,sSeconds);
    }

    private void setTimeInLabelOfGame(String s) {
        instance.setLabelTime(s);
    }
}
