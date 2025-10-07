package view;

import domain.Quiz;
import domain.QuizConstants;
import domain.Student;
import file.StudentsInput;

import java.util.List;

public class OutputView {

    /**
     * 인사말
     */
    public void printHello() {
        System.out.println("카카오 테크 부트캠프 이름맞추기 퀴즈에 오신걸 환영합니다.");
    }

    public void printGoodbye() {
        System.out.println("이용해 주셔서 감사합니다 퀴즈를 종료합니다.");
    }

    public void printScore(Student player, int playerScore) {
        System.out.println(player.getKoreanName() + "님의 총 점수는 = " + playerScore + " 입니다");
    }


    /**
     * 퀴즈 진행
     */
    public void printQuizTypes() {
        System.out.println("-------------------------");
        System.out.println("1. 주관식 퀴즈");
        System.out.println("2. 객관식 퀴즈");
        System.out.println("3. 주관식 - 애너그램 퀴즈");
        System.out.println("5. 퀴즈 종료");
        System.out.println("-------------------------");
    }

    public void printQuiz(Quiz quiz) {
        System.out.println(quiz.getQuestion());
    }

    public void printCorrectMsg(Quiz quiz) {
        System.out.println("정답입니다! 획득 점수:" + quiz.getScore());
    }

    public void printWrongMsg(Quiz quiz) {
        System.out.println("오답입니다! 해당퀴즈점수:" + quiz.getScore());
        System.out.println("정답 = " + quiz.getAnswer());
    }

    /**
     * 퀴즈 타이머
     */
    public void printTimeLimitMs() {
        System.out.println("제한시간은 " + QuizConstants.QUIZ_TIME_LIMIT + "초 입니다!");
    }

    public void printTimeLeft(int leftTime) {
        System.out.println(leftTime + "초 남았습니다!!");
    }

    public void printTimeOverMsg() {
        System.out.println("시간 종료!");
    }

    public void printTimeOverInputIgnored() {
        System.out.println("시간이 초과되어 입력은 무효처리 됩니다.");
    }

}
