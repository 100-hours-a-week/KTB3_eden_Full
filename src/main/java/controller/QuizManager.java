package controller;

import domain.Quiz;
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
                case 1 -> playerScore = startQuiz(playerScore, () -> generateSubjectiveQuiz(player));
                case 2 -> playerScore = startQuiz(playerScore, () -> generateObjectiveQuiz(player, studentCatalog));
                case 3 -> playerScore = startQuiz(playerScore, () -> generateAnagramQuiz(player));
                case 5 -> {
                    break;
                }
                default ->{
                    System.out.println("명시된 퀴즈 번호를 입력해주세요");
                }
            }
            if(inputQuizTypeInput == 5) break;
        }
        System.out.println("총 점수 = " + playerScore);
    }

    private int startQuiz(int playerScore, Supplier<? extends Quiz> quizSupplier) {
        Quiz quiz = quizSupplier.get();
        return quizProgress(playerScore, quiz);
    }

    private int quizProgress(int playerScore, Quiz quiz) {
        outputView.printQuiz(quiz);
        String answerInput = inputView.readAnswer();

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

    public int inputQuizTypeInput(){
        while(true){
            try {
                outputView.printQuizTypes();
                return inputView.readQuizType();
            }
            catch (QuizException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Student validateStudent(List<String> rawStudent) throws QuizException {
        return studentCatalog.findByKoreanNameAndCourse(rawStudent.get(0), rawStudent.get(1));
    }
}
