package exception;

public enum QuizExceptionType {

    EMPTY_INPUT("입력값이 비어있습니다"),
    EMPTY_SEPARATOR("이름과 과정명 사이에 - 를 입력해야합니다."),
    INVALID_INPUT_NAME_COURSE("이름이나 코스명이 제대로 입력되지않았습니다."),
    INVALID_INPUT_COURSE("수강명 이름이 잘못되었습니다."),
    INVALID_INPUT_QUIZ_TYPE("퀴즈번호는 숫자만 입력해주세요"),
    INVALID_INPUT_QUIZ_NUMBER("퀴즈번호 범위에 맞게 입력해주세요"),
    NOT_FOUND_STUDENT("해당 하는 학생을 찾을 수 없습니다.");

    private String message;

    QuizExceptionType(String s) {
        this.message = s;
    }

    public String getMessage() {
        return message;
    }
}
