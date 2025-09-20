package domain.quiztype.engnamequiz.objective.subjective;

import domain.Quiz;
import domain.Student;
import static domain.QuizConstants.*;

public class SubjectiveQuiz extends Quiz {

    public SubjectiveQuiz(Student student) {
        this.student = student;
        generateQuiz();
    }

    @Override
    protected void generateQuiz() {
        question = "학생 " + student.getKoreanName() + "의 영어 이름은? 입력형식)kevin";
        answer = student.getEngFirstName();
        score = SUBJECTIVE_QUIZ_SCORE;
    }

    @Override
    public boolean isCorrect(String answerInput) {
        return answerInput.equals(student.getEngFirstName());
    }
}
