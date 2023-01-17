package app.dto.request;

import app.domain.GradeLevel;
import app.domain.Subject;

public class EditGradeDto {
    private Long studentId;

    private String description;

    private Subject subject;

    private GradeLevel gradeLevel;

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public GradeLevel getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(GradeLevel gradeLevel) {
        this.gradeLevel = gradeLevel;
    }
}
