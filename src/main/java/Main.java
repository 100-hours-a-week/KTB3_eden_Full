import controller.QuizManager;
import domain.Student;
import domain.StudentCatalog;
import file.StudentsInput;
import view.InputParser;
import view.InputView;
import view.OutputView;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        InputParser inputParser = new InputParser();
        InputView inputView = new InputView(inputParser);
        OutputView outputView = new OutputView();

        List<Student> students = StudentsInput.readStudents();
        StudentCatalog studentCatalog = new StudentCatalog(students);

        QuizManager quizManager = new QuizManager(inputView, outputView,studentCatalog);
        quizManager.run();
    }
}