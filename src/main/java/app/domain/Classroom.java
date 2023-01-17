package app.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Data
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    @Column(name = "teacherId")
    private Long teacherId;

    @OneToMany
    @JoinColumn(name = "classroomId", referencedColumnName = "id")
    private List<ApplicationUser> students;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
