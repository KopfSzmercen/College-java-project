package app.dto.request;

import lombok.Data;
import javax.validation.constraints.NotNull;

@Data
public class CreateClassroomDto {

    @NotNull(message = "Classroom name is required")
    private String classroomName;
}
