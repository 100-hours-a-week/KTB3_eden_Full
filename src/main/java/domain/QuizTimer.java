package domain;

public class QuizTimer implements Runnable {

    private volatile boolean timeOver = false;
    private int time;

    public QuizTimer(int seconds) {
        this.time = seconds;
    }

    @Override
    public void run() {
        System.out.println("제한시간은 " + time + "초 입니다!");
        while (time > 0) {
            try {
                Thread.sleep(1000);
                time--;
                if (time % 10 == 0 && time != 0) System.out.println(time + "초 남았습니다!!");
            } catch (InterruptedException e) { return; }
        }
        timeOver = true;
        System.out.println("시간 종료!");
    }

    public boolean isTimeOver() {
        return timeOver;
    }
}
