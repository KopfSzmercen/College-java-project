package app.dto.request;

import app.domain.GradeLevel;
import app.domain.Subject;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class CreateGradeDto {

    @NotNull
    private Long studentId;

    @NotNull
    private String description;

    @NotNull
    private Subject subject;

    @NotNull
    private GradeLevel gradeLevel;
}
