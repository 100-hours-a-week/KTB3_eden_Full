package controller;

import domain.Student;
import domain.StudentCatalog;
import domain.quiztype.objective.ObjectiveQuiz;
import domain.quiztype.subjective.AnagramQuiz;
import domain.quiztype.subjective.SubjectiveQuiz;
import view.InputView;
import view.OutputView;

import java.util.List;

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

        while (true) {
            int inputQuizTypeInput = inputQuizTypeInput();
            switch (inputQuizTypeInput) {
                case 1:
                    playerScore = startSubjectiveQuiz(player, playerScore);
                    break;
                case 2:
                    playerScore = startObjectiveQuiz(player, playerScore);
                    break;
                case 3:
                    playerScore = startAnagramQuiz(player, playerScore);
                case 5:
                    break;
                default:
                    System.out.println("입력형식이 올바르지 않습니다");
            }
            if(inputQuizTypeInput == 5) break;
        }
        System.out.println("총 점수 = " + playerScore);
    }

    private int startObjectiveQuiz(Student player, int playerScore) {
        ObjectiveQuiz quiz = generateObjectiveQuiz(player, studentCatalog);
        playerScore = quizProgress(playerScore, quiz);
        return playerScore;
    }

    private int startSubjectiveQuiz(Student player, int playerScore) {
        SubjectiveQuiz quiz = generateSubjectiveQuiz(player);
        playerScore = quizProgress(playerScore, quiz);
        return playerScore;
    }

    private int startAnagramQuiz(Student player, int playerScore) {
        AnagramQuiz quiz = generateAnagramQuiz(player);
        playerScore = quizProgress(playerScore, quiz);
        return playerScore;
    }

    private int quizProgress(int playerScore, ObjectiveQuiz quiz) {
        outputView.printQuiz(quiz);
        int answerInput = inputView.readOptionAnswer();

        if (answerInput != -1 && quiz.isCorrect(quiz.getOption(answerInput))) {
            outputView.printCorrectMsg(quiz);
            playerScore += quiz.getScore();
        } else outputView.printWrongMsg(quiz);

        return playerScore;
    }

    private int quizProgress(int playerScore, SubjectiveQuiz quiz) {
        outputView.printQuiz(quiz);
        String answerInput = inputView.readAnswer();

        if (quiz.isCorrect(answerInput)) {
            outputView.printCorrectMsg(quiz);
            playerScore += quiz.getScore();
        } else outputView.printWrongMsg(quiz);

        return playerScore;
    }

    private int quizProgress(int playerScore, AnagramQuiz quiz) {
        outputView.printQuiz(quiz);
        String answerInput = inputView.readAnswer();

        if (quiz.isCorrect(answerInput)) {
            outputView.printCorrectMsg(quiz);
            playerScore += quiz.getScore();
        } else outputView.printWrongMsg(quiz);

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
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public int inputQuizTypeInput(){
        while(true){
            try {
                outputView.printQuizTypes();
                return inputView.readQuizType();
            }
            catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Student validateStudent(List<String> rawStudent){
        return studentCatalog.findByKoreanNameAndCourse(rawStudent.get(0), rawStudent.get(1));
    }
}
