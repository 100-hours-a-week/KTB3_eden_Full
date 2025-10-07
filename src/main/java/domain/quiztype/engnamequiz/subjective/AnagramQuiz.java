package domain.quiztype.engnamequiz.subjective;

import domain.Student;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
        String engFirstName = student.getEngFirstName();
        List<Character> ans = engFirstName.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());

        Collections.shuffle(ans);
        return ans;
    }
}
