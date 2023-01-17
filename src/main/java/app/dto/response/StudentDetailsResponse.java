package app.dto.response;

import app.domain.Grade;
import lombok.Data;

import java.util.List;

@Data
public class StudentDetailsResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private List<Grade> studentGrades;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public List<Grade> getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(List<Grade> studentGrades) {
        this.studentGrades = studentGrades;
    }
}
