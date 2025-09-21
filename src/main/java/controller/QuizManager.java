package controller;

import domain.Quiz;
import domain.QuizTimer;
import domain.Student;
import domain.StudentCatalog;
import domain.quiztype.engnamequiz.objective.ObjectiveQuiz;
import domain.quiztype.engnamequiz.objective.subjective.AnagramQuiz;
import domain.quiztype.engnamequiz.objective.subjective.SubjectiveQuiz;
import exception.QuizException;
import view.InputView;
import view.OutputView;
import java.util.List;
import java.util.function.Supplier;

import static domain.QuizConstants.*;

public class QuizManager {
    private final InputView inputView;
    private final OutputView outputView;
    private final StudentCatalog studentCatalog;

    public QuizManager(InputView inputView, OutputView outputView, StudentCatalog studentCatalog) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.studentCatalog = studentCatalog;
    }

    public void run() {
        outputView.printHello();
        Student player = inputStudent();
        quizStart(player);
        outputView.printGoodbye();
    }

    public void quizStart(Student player) {
        int playerScore = 0;
        QuizTimer timer = new QuizTimer(QUIZ_TIME_LIMIT);
        Thread timerThread = new Thread(timer);
        timerThread.start();

        while (!timer.isTimeOver()) {
            int inputQuizTypeInput = inputQuizTypeInput(timer);
            switch (inputQuizTypeInput) {
                case 1 -> playerScore = startQuiz(playerScore, timer, () -> generateSubjectiveQuiz(player));
                case 2 -> playerScore = startQuiz(playerScore, timer, () -> generateObjectiveQuiz(player, studentCatalog));
                case 3 -> playerScore = startQuiz(playerScore, timer, () -> generateAnagramQuiz(player));
                case 5 -> {
                    break;
                }
                default -> {
                    System.out.println("퀴즈 범위에 맞게 입력해주세요");
                }
            }
            if (inputQuizTypeInput == 5) break;
        }
        outputView.printScore(player, playerScore);
    }

    private int startQuiz(int playerScore, QuizTimer timer, Supplier<? extends Quiz> quizSupplier) {
        Quiz quiz = quizSupplier.get();
        return quizProgress(playerScore, timer, quiz);
    }

    private int quizProgress(int playerScore, QuizTimer timer, Quiz quiz) {
        outputView.printQuiz(quiz);
        String answerInput = inputView.readAnswer();

        if(timer.isTimeOver()) {
            System.out.println("시간이 초과되어 입력은 무효처리 됩니다.");
            return playerScore;
        }

        if(quiz.isCorrect(answerInput)) {
            outputView.printCorrectMsg(quiz);
            playerScore += quiz.getScore();
        } else  { outputView.printWrongMsg(quiz); }

        return playerScore;
    }

    public SubjectiveQuiz generateSubjectiveQuiz(Student player) {
        SubjectiveQuiz quiz = new SubjectiveQuiz(getSameCourseStudent(player));
        return quiz;
    }

    public ObjectiveQuiz generateObjectiveQuiz(Student player, StudentCatalog studentCatalog) {
        ObjectiveQuiz quiz = new ObjectiveQuiz(getSameCourseStudent(player),studentCatalog);
        return quiz;
    }

    public AnagramQuiz generateAnagramQuiz(Student player) {
        AnagramQuiz quiz = new AnagramQuiz(getSameCourseStudent(player));
        return quiz;
    }

    public Student getSameCourseStudent(Student player) {
        return studentCatalog.getRandomStudentFromSameCourse(player);
    }

    public Student inputStudent(){
        while(true){
            try {
                List<String> rawStudent = inputView.readStudent();
                return validateStudent(rawStudent);
            } catch (QuizException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int inputQuizTypeInput(QuizTimer timer) {
        final int[] result = {-1};
        Thread inputThread = new Thread(() -> {
            try {
                outputView.printQuizTypes();
                result[0] = inputView.readQuizType();
            } catch (QuizException e) {
                if(!timer.isTimeOver()) System.out.println(e.getMessage());
            }
        });

        inputThread.start();

        while(inputThread.isAlive()){
            if(timer.isTimeOver()){
                inputThread.interrupt();
                return 5;
            }
        }
        return result[0];
    }

    public Student validateStudent(List<String> rawStudent) throws QuizException {
        return studentCatalog.findByKoreanNameAndCourse(rawStudent.get(0), rawStudent.get(1));
    }
}
