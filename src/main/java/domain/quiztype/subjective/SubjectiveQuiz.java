package domain.quiztype.subjective;

import domain.Quiz;
import domain.Student;

public class SubjectiveQuiz extends Quiz {

    private static final int SUBJECTIVE_SCORE = 10;

    public SubjectiveQuiz(Student student) {
        super(student);
        generateQuiz();
    }

    protected void generateQuiz() {
        question = "학생 " + student.getKoreanName() + "의 영어 이름은? 입력형식)kevin";
        score = SUBJECTIVE_SCORE;
    }
}
