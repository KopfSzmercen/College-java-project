package app.mappers;

import app.domain.Classroom;
import app.dto.response.ClassroomBasicInfoResponse;
import app.dto.response.StudentBasicInfoResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ClassroomBasicInfoMapper {

    public List<ClassroomBasicInfoResponse> toDtos(List<Classroom> classrooms) {
        List<ClassroomBasicInfoResponse> dtos = new ArrayList<>();

        for (var classroom : classrooms) {
            List<StudentBasicInfoResponse> classroomStudents = new ArrayList<>();
            for (var student : classroom.getStudents()) {
                classroomStudents.add(new StudentBasicInfoResponse(student.getId(), student.getFirstName(), student.getLastName(), student.getEmailAddress()));
            }
            dtos.add(new ClassroomBasicInfoResponse(classroom.getId(), classroom.getName(), classroom.getTeacherId(), classroomStudents));
        }
        return dtos;
    }
}
