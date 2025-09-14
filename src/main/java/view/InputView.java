package view;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputView {

    private final InputParser inputParser;

    public InputView(InputParser inputParser) {
        this.inputParser = inputParser;
    }

    public List<String> readStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("본인의 이름과 과정명을 입력해주세요.(이정원-풀스택, 이정원-AI, 이정원-클라우드)");
        String input = scanner.nextLine();
        inputParser.validateStudentInput(input);
        return Arrays.asList(input.split("-"));
    }

    public String readAnswer() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public int readOptionAnswer() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("정답번호를 입력하세요.");
        String input = scanner.nextLine();
        return inputParser.validateOptionInput(input);
    }

    public int readQuizType() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("진행할 문제 번호를 입력해주세요.");
        String input = scanner.nextLine();
        inputParser.validateQuizTypeInput(input);
        return Integer.parseInt(input);
    }
}
