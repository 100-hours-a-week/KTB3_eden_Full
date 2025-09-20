package domain;

public abstract class Quiz {
    protected Student student;
    protected String question;
    protected String answer;
    protected int score;

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    public int getScore() { return score; }

    protected abstract void generateQuiz();
    public abstract boolean isCorrect(String answerInput);
}
