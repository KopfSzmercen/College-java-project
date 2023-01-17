package app.mappers;

import app.domain.ApplicationUser;
import app.dto.response.StudentBasicInfoResponse;
import app.dto.response.StudentDetailsResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@Service
public class StudentDetailInfoMapper {

    public StudentDetailsResponse toDto(ApplicationUser student) {
        var studentDetailsDto = new StudentDetailsResponse();

        studentDetailsDto.setId(student.getId());
        studentDetailsDto.setStudentGrades(student.getStudentGrades());
        studentDetailsDto.setEmailAddress(student.getEmailAddress());
        studentDetailsDto.setFirstName(student.getFirstName());
        studentDetailsDto.setLastName(student.getLastName());

        return studentDetailsDto;
    }

    public List<StudentBasicInfoResponse> toDtos(ApplicationUser teacher) {
        var students = new ArrayList<StudentBasicInfoResponse>();

        var teacherClassrooms = teacher.getOwnedClassrooms();

        for (var classroom : teacherClassrooms) {
            var studentsInClassroom = classroom.getStudents();

            for (var student : studentsInClassroom) {
                var studentDto = new StudentBasicInfoResponse(student.getId(), student.getFirstName(), student.getLastName(), student.getEmailAddress());
                students.add(studentDto);
            }
        }

        return students;
    }
}
