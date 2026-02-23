package domain;

public class Student {
    private String groupNumber;
    private String courseName;
    private String koreanName;
    private String engFirstName;
    private String engLastName;

    public Student(String groupNumber, String courseName, String koreanName, String engFirstName, String engLastName) {
        this.groupNumber = groupNumber;
        this.courseName = courseName;
        this.koreanName = koreanName;
        this.engFirstName = engFirstName;
        this.engLastName = engLastName;
    }

    public String getKoreanName() {
        return koreanName;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getEngFirstName() {
        return engFirstName;
    }
}
