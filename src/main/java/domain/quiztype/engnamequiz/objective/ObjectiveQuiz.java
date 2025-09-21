package domain.quiztype.engnamequiz.objective;

import domain.Quiz;
import domain.Student;
import domain.StudentCatalog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static domain.QuizConstants.*;

public class ObjectiveQuiz extends Quiz {


    protected List<String> options;
    protected StudentCatalog studentCatalog;

    public ObjectiveQuiz(Student student, StudentCatalog studentCatalog) {
        this.student = student;
        this.studentCatalog = studentCatalog;
        generateQuiz();
    }

    @Override
    protected void generateQuiz() {
        generateQuestion();
        answer = student.getEngFirstName();
        score = OBJECTIVE_QUIZ_SCORE;
    }

    private void generateQuestion() {
        StringBuilder sb = new StringBuilder();
        sb.append("문제 : ").append(student.getKoreanName()).append("의 영어 이름은?\n");
        options = generateOptions();

        for(int i = 0; i < options.size(); i++){
            String option = options.get(i);
            sb.append(i+1).append(".").append(option).append(" ");
        }
        question = sb.toString();
    }

    protected List<String> generateOptions() {
        options = new ArrayList<>();
        options.add(student.getEngFirstName());
        for (int i = 0; i < OPTIONS_SIZE-1; i++) {
            options.add(getStudentOptions((student)).getEngFirstName());
        }
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
        int indexInput = validateOptionInput(answerInput);
        return getOption(indexInput).equals(answer);
    }

    public int validateOptionInput(String rawOptionInput) {
        try {
            int indexInt = Integer.parseInt(rawOptionInput.trim());
            if(indexInt < 1 || indexInt > OPTIONS_SIZE) return -1;
            return indexInt;
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
