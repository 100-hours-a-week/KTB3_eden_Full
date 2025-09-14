package file;

import domain.Student;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentsInput {

    private static final String FILE_PATH = "src/main/resources/students.csv";

    public static List<Student> readStudents() {
        List<String> lines = readLines();
        List<Student> students = new ArrayList<>();

        for (String line : lines) {
            Student student = parseStudent(line);
            students.add(student);
        }

        return students;
    }

    private static List<String> readLines() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(FILE_PATH))) {
            reader.readLine();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static Student parseStudent(String line) {
        List<String> rawStudent = Arrays.stream(line.split(",")).toList();
        String groupName = rawStudent.get(0);
        String courseName = rawStudent.get(1);
        String koreanName = rawStudent.get(2);
        String engFullName = rawStudent.get(3);
        List<String> sepEngFullname = Arrays.stream(engFullName.split("\\.")).toList();

        String engFirstName = sepEngFullname.get(0);
        String engLastName = sepEngFullname.get(1);

        return new Student(groupName, courseName, koreanName, engFirstName, engLastName);
    }

}
