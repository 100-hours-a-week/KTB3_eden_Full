package exception;

public class QuizException extends RuntimeException {

    private final QuizExceptionType type;

    public QuizException(QuizExceptionType quizExceptionType) {
        super(quizExceptionType.getMessage());
        this.type = quizExceptionType;
    }
}
