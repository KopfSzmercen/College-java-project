package app.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;


@Entity
@Data
public class ApplicationUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    @JsonIgnore
    @Email
    private String emailAddress;

    @JsonIgnore
    private String passwordHash;

    @JsonIgnore
    private UserRole role;

    @JsonIgnore
    private LocalDate registrationDate;

    @JsonIgnore
    @OneToMany
    @JoinColumn(name = "teacherId", referencedColumnName = "id")
    private List<Classroom> ownedClassrooms;


    @OneToMany
    @JoinColumn(name = "teacherId", referencedColumnName = "id")
    private List<Grade> madeGrades;


    @OneToMany
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private List<Grade> studentGrades;

    @JsonIgnore
    @Column(name = "classroomId")
    private Long classroomId = null;

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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<Classroom> getOwnedClassrooms() {
        return ownedClassrooms;
    }

    public void setOwnedClassrooms(List<Classroom> ownedClassrooms) {
        this.ownedClassrooms = ownedClassrooms;
    }

    public List<Grade> getMadeGrades() {
        return madeGrades;
    }

    public void setMadeGrades(List<Grade> madeGrades) {
        this.madeGrades = madeGrades;
    }

    public List<Grade> getStudentGrades() {
        return studentGrades;
    }

    public void setStudentGrades(List<Grade> studentGrades) {
        this.studentGrades = studentGrades;
    }

    public Long getClassroomId() {
        return classroomId;
    }

    public void setClassroomId(Long classroomId) {
        this.classroomId = classroomId;
    }
}
