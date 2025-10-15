package domain.quiztype.engnamequiz.objective;

import domain.Quiz;
import domain.Student;
import domain.StudentCatalog;
import view.InputParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static domain.QuizConstants.*;

public class ObjectiveQuiz extends Quiz {

    protected List<String> options;
    protected StudentCatalog studentCatalog;
    private final InputParser inputParser;

    public ObjectiveQuiz(Student student, StudentCatalog studentCatalog, InputParser inputParser) {
        this.student = student;
        this.studentCatalog = studentCatalog;
        this.inputParser = inputParser;
        generateQuiz();
    }

    @Override
    protected void generateQuiz() {
        generateQuestion();
        answer = student.getEngFirstName();
        score = OBJECTIVE_QUIZ_SCORE;
    }

    private void generateQuestion() {
        options = generateOptions();
        String optionsText = IntStream.range(0, options.size())
                .mapToObj(i -> (i+1) + "." + options.get(i))
                .collect(Collectors.joining(" "));

        question = String.format("문제 : %s의 영어 이름은? \n%s", student.getKoreanName(), optionsText);
    }

    protected List<String> generateOptions() {
        options = new ArrayList<>();
        options.add(student.getEngFirstName());
        options.addAll(
                IntStream.range(0, OPTIONS_SIZE -1)
                        .mapToObj(i -> getStudentOptions(student).getEngFirstName())
                        .toList()
        );
        Collections.shuffle(options);
        return options;
    }

    protected Student getStudentOptions(Student player) {
        while (true) {
            Student randomStudent = studentCatalog.getRandomStudent();
            if (!randomStudent.equals(player)) {
                return randomStudent;
            }
        }
    }

    public String getOption(int userInput) {
        return options.get(userInput - 1);
    }

    @Override
    public boolean isCorrect(String answerInput){
        int indexInput = inputParser.validateOptionInput(answerInput);
        if(indexInput == WRONG_ANSWER) return false;

        return getOption(indexInput).equals(answer);
    }
}
