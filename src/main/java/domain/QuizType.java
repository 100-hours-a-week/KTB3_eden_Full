package domain;

import exception.QuizException;

import java.util.Arrays;

import static exception.QuizExceptionType.INVALID_INPUT_QUIZ_NUMBER;

public enum QuizType {
    SUBJECTIVE_QUIZ(1),
    OBJECTIVE_QUIZ(2),
    ANAGRAM_QUIZ(3),

    EXIT_QUIZ(5);

    private final int quizTypeNumber;

    QuizType(int quizTypeNumber) {
        this.quizTypeNumber = quizTypeNumber;
    }

    public int getQuizTypeNumber() {
        return quizTypeNumber;
    }

    public static QuizType fromNumber(int number) {
        return Arrays.stream(QuizType.values())
                .filter(quizType -> quizType.getQuizTypeNumber() == number)
                .findFirst()
                .orElseThrow(() -> new QuizException(INVALID_INPUT_QUIZ_NUMBER));
    }

}
