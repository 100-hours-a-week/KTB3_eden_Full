package view;


import exception.QuizException;

import static domain.QuizConstants.OPTIONS_SIZE;
import static exception.QuizExceptionType.*;

public class InputParser {

    public void validateStudentInput(String rawStudent) {
        if (rawStudent == null || rawStudent.isEmpty()) {
            throw new QuizException(EMPTY_INPUT);
        }
        String[] parts = rawStudent.split("-");
        if (parts.length != 2) {
            throw new QuizException(EMPTY_SEPARATOR);
        }

        String name = parts[0].trim();
        String course = parts[1].trim();

        if (name.isEmpty() || course.isEmpty()) {
            throw new QuizException(INVALID_INPUT_NAME_COURSE);
        }
    }

    public int validateOptionInput(String rawOptionInput) {
        try {
            int indexInt = Integer.parseInt(rawOptionInput.trim());
            if(indexInt<1 || indexInt > OPTIONS_SIZE) return -1;
            return indexInt;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public void validateQuizTypeInput(String rawQuizTypeInput){
        try{
            Integer.parseInt(rawQuizTypeInput);
        } catch (NumberFormatException e) {
            throw new QuizException(INVALID_INPUT_QUIZ_TYPE);
        }
    }
}
