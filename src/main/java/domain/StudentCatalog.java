package domain;

import exception.QuizException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import static exception.QuizExceptionType.*;
import static java.util.stream.Collectors.toList;

public class StudentCatalog {
    private List<Student> students;
    private final Map<String, List<Student>> studentsByCourse;
    private int generation;

    public StudentCatalog(List<Student> students) {
        this.students = students;
        this.studentsByCourse = students.stream()
                .collect(Collectors.groupingBy(Student::getCourseName));
        this.generation = 3;
    }

    public Student getRandomStudent() {
        Random random = new Random();
        int randomNumber = random.nextInt(students.size());
        return students.get(randomNumber);
    }

    public Student getRandomStudentFromSameCourse(Student player) {
        Random random = new Random();
        List<Student> sameCourseStudents = studentsByCourse.get(player.getCourseName());
        int randomNumber = random.nextInt(sameCourseStudents.size());
        return sameCourseStudents.get(randomNumber);
    }

    // 한국이름으로 학생 리스트 반환
    private List<Student> findStudentsByKoreanName(String koreanName) {
        return students.stream()
                .filter(student -> student.getKoreanName().equals(koreanName))
                .collect(toList());
    }

    // 이름과 수강명으로 학생 반환 -> 같은 수강에는 동명이인 없다고 가정
    public Student findByKoreanNameAndCourse(String koreanName, String courseName){
        List<Student> studentsInCourse = studentsByCourse.get(courseName);
        if(studentsInCourse == null){
            throw new QuizException(INVALID_INPUT_COURSE);
        }

        return studentsInCourse.stream()
                .filter(student -> student.getKoreanName().equals(koreanName))
                .findFirst()
                .orElseThrow(() -> new QuizException(NOT_FOUND_STUDENT));
    }
}
