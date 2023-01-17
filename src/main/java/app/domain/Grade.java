package app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Grade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "studentId")
    private long studentId;

    @JsonIgnore
    @Column(name = "teacherId")
    private long teacherId;

    @Column(name = "subject")
    private Subject subject;


    @Column(name = "level")
    private GradeLevel level;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(long teacherId) {
        this.teacherId = teacherId;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public GradeLevel getLevel() {
        return level;
    }

    public void setLevel(GradeLevel level) {
        this.level = level;
    }
}
