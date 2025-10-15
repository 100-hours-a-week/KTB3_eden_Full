package controller;

import domain.*;
import domain.quiztype.engnamequiz.objective.ObjectiveQuiz;
import domain.quiztype.engnamequiz.subjective.AnagramQuiz;
import domain.quiztype.engnamequiz.subjective.SubjectiveQuiz;
import exception.QuizException;
import exception.QuizExceptionType;
import view.InputParser;
import view.InputView;
import view.OutputView;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static domain.QuizConstants.*;

public class QuizManager {
    private final InputParser inputParser;
    private final InputView inputView;
    private final OutputView outputView;
    private final StudentCatalog studentCatalog;
    private final Map<QuizType, Function<Student, Quiz>> quizGenerators;

    public QuizManager(InputParser inputParser, InputView inputView, OutputView outputView, StudentCatalog studentCatalog) {
        this.inputParser = inputParser;
        this.inputView = inputView;
        this.outputView = outputView;
        this.studentCatalog = studentCatalog;

        this.quizGenerators = Map.of(
                QuizType.SUBJECTIVE_QUIZ, player -> new SubjectiveQuiz(getSameCourseStudent(player)),
                QuizType.OBJECTIVE_QUIZ, player -> new ObjectiveQuiz(getSameCourseStudent(player), this.studentCatalog, inputParser),
                QuizType.ANAGRAM_QUIZ, player -> new AnagramQuiz(getSameCourseStudent(player))
        );
    }

    public void run() {
        outputView.printHello();
        Student player = inputStudent();
        quizStart(player);
        outputView.printGoodbye();
    }

    public void quizStart(Student player) {
        int playerScore = 0;
        QuizTimer timer = new QuizTimer(QUIZ_TIME_LIMIT, outputView);
        Thread timerThread = new Thread(timer);
        timerThread.start();

        while (!timer.isTimeOver()) {
            int inputQuizTypeInput = inputQuizTypeInput(timer);
            QuizType quizType;
            try {
                quizType = QuizType.fromNumber(inputQuizTypeInput);
            } catch (QuizException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (quizType == QuizType.EXIT_QUIZ) {
                timerThread.interrupt();
                break;
            }
            Function<Student, Quiz> generator = quizGenerators.get(quizType);
            if(generator != null) {
                playerScore = startQuiz(playerScore, timer, () -> generator.apply(player));
            }
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
            outputView.printTimeOverInputIgnored();
            return playerScore;
        }

        if(quiz.isCorrect(answerInput)) {
            outputView.printCorrectMsg(quiz);
            playerScore += quiz.getScore();
        } else  { outputView.printWrongMsg(quiz); }

        return playerScore;
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
                return QuizType.EXIT_QUIZ.getQuizTypeNumber();
            }
        }
        return result[0];
    }

    public Student validateStudent(List<String> rawStudent) throws QuizException {
        return studentCatalog.findByKoreanNameAndCourse(rawStudent.get(0), rawStudent.get(1))
                .orElseThrow(() -> new QuizException(QuizExceptionType.NOT_FOUND_STUDENT));
    }
}
