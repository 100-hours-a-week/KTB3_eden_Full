package view;


public class InputParser {

    private static final String INVALID_INPUT = "입력이 올바르지 않습니다";
    private static final int OPTIONS_SIZE = 3;

    public void validateStudentInput(String rawStudent) {
        if (rawStudent == null || rawStudent.isEmpty()) {
            throw new IllegalArgumentException(INVALID_INPUT);
        }
        String[] parts = rawStudent.split("-");
        if (parts.length != 2) {
            throw new IllegalArgumentException(INVALID_INPUT);
        }

        String name = parts[0].trim();
        String course = parts[1].trim();

        if (name.isEmpty() || course.isEmpty()) {
            throw new IllegalArgumentException(INVALID_INPUT);
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
            throw new IllegalArgumentException(INVALID_INPUT);
        }
    }
}
