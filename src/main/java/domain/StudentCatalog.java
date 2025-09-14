package domain;

import java.util.List;
import java.util.Random;

import static java.util.stream.Collectors.toList;

public class StudentCatalog {
    private List<Student> students;
    private int generation;

    public StudentCatalog(List<Student> students) {
        this.students = students;
        this.generation = 3;
    }

    public Student getRandomStudent() {
        Random random = new Random();
        int randomNumber = random.nextInt(students.size());
        return students.get(randomNumber);
    }

    public Student getRandomStudentFromSameCourse(Student player) {
        Random random = new Random();

        List<Student> samecourseStudets = students.stream()
                .filter(student -> student.getCourseName().equals(player.getCourseName()))
                .collect(toList());

        int randomNumber = random.nextInt(samecourseStudets.size());

        return samecourseStudets.get(randomNumber);
    }

    public Student findByKoreanNameAndCourse(String koreanName, String courseName) {
        List<Student> studentsByKoreanName = findStudentsByKoreanName(koreanName);
        validateStudentExistsByKoreanName(studentsByKoreanName);
        Student findStudent = findByCourseNameAndStudents(courseName, studentsByKoreanName);
        return findStudent;
    }

    // 한국이름으로 학생 리스트 반환
    private List<Student> findStudentsByKoreanName(String koreanName) {
        return students.stream()
                .filter(student -> student.getKoreanName().equals(koreanName))
                .collect(toList());
    }

    // 이름으로 찾은 학생들 중 수강명 일치하는 학생 반환
    private static Student findByCourseNameAndStudents(String courseName, List<Student> studentsByKoreanName) {
        return studentsByKoreanName.stream()
                .filter(student -> student.getCourseName().equals(courseName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("수강 과정 입력이 잘못되었습니다."));
    }

    // 이름 오입력 예외
    private static void validateStudentExistsByKoreanName(List<Student> studentsByKoreanName) {
        if(studentsByKoreanName.isEmpty()) throw new IllegalArgumentException("해당 이름을 가진 학생이 없습니다.");
    }

}
