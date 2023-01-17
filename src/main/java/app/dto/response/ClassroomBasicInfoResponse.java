package app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ClassroomBasicInfoResponse {


    private Long id;


    private String name;


    private Long teacherId;

    private List<StudentBasicInfoResponse> students;
}
