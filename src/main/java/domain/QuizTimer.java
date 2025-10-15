package domain;

import view.OutputView;

public class QuizTimer implements Runnable {

    private volatile boolean timeOver = false;
    private int time;
    private final OutputView outputView;

    public QuizTimer(int seconds, OutputView outputView) {
        this.time = seconds;
        this.outputView = outputView;
    }

    @Override
    public void run() {
        outputView.printTimeLimitMs();
        while (time > 0) {
            try {
                Thread.sleep(1000);
                time--;
                if (time % 10 == 0 && time != 0) outputView.printTimeLeft(time);
            } catch (InterruptedException e) { return; }
        }
        timeOver = true;
        outputView.printTimeOverMsg();
    }

    public boolean isTimeOver() {
        return timeOver;
    }
}
