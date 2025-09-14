package domain.quiztype.objective;

import domain.Quiz;
import domain.Student;
import domain.StudentCatalog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ObjectiveQuiz extends Quiz {

    private static final int OBJECTIVE_QUIZ_SCORE = 3;
    private static final int OPTIONSIZE = 3;

    protected List<String> options;
    protected StudentCatalog studentCatalog;

    public ObjectiveQuiz(Student student, StudentCatalog studentCatalog) {
        super(student);
        this.studentCatalog = studentCatalog;
        generateQuiz();
    }

    protected void generateQuiz() {
        StringBuilder sb = new StringBuilder();
        sb.append("문제 : ").append(student.getKoreanName()).append("의 영어 이름은?\n");
        options = generateOptions();

        for(int i = 0; i < options.size(); i++){
            String option = options.get(i);
            sb.append(i+1).append(".").append(option).append(" ");
        }
        question = sb.toString();
        score = OBJECTIVE_QUIZ_SCORE;
    }

    protected List<String> generateOptions() {
        options = new ArrayList<>();
        options.add(student.getEngFirstName());
        for (int i = 0; i < OPTIONSIZE; i++) {
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
        return options.get(userInput-1);
    }
}
