package domain;

public class Quiz {
    protected Student student;
    protected String question;
    protected String answer;
    protected int score;

    public Quiz(Student student) {
        this.student = student;
        this.answer = student.getEngFirstName();
        this.score = 1;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getScore() {
        return score;
    }

    public Boolean isCorrect(String answerInput) {
        return answerInput.equals(this.answer);
    }
}
