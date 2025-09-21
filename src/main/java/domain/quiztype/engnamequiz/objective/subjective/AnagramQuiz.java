package domain.quiztype.engnamequiz.objective.subjective;

import domain.Student;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static domain.QuizConstants.*;

public class AnagramQuiz extends SubjectiveQuiz {

    public AnagramQuiz(Student student) {
        super(student);
        generateQuiz();
    }

    @Override
    protected void generateQuiz() {
        List<Character> anagram = generateAnagram();
        question = anagram.toString() + "의 알파벳을 가진 학생의 영어이름은?";
        answer = student.getEngFirstName();
        score = ANAGRAM_QUIZ_SCORE;
    }

    private List<Character> generateAnagram() {
        List<Character> ans = new ArrayList<>();
        String engFirstName = student.getEngFirstName();
        for(int i = 0; i < engFirstName.length(); i++) {
            ans.add(engFirstName.charAt(i));
        }
        Collections.shuffle(ans);
        return ans;
    }
}
