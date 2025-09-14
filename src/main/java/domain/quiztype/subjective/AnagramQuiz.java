package domain.quiztype.subjective;

import domain.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnagramQuiz extends SubjectiveQuiz {

    private static final int ANAGRAM_SCORE = 7;

    public AnagramQuiz(Student student) {
        super(student);
    }

    protected void generateQuiz() {
        List<Character> anagram = generateAnagram();
        question = anagram.toString() + "의 알파벳을 가진 학생의 영어이름은?";
        score = ANAGRAM_SCORE;
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
