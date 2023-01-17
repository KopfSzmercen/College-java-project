package app.services;

import app.domain.ApplicationUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service
public class ServicesUtil {
    public boolean isStudentInAnyOfTeacherClassrooms(@NotNull ApplicationUser teacher, long studentId) {

        var classrooms = teacher.getOwnedClassrooms();
        for (var classroom : classrooms) {
            for (var classroomStudent : classroom.getStudents()) {
                if (classroomStudent.getId().equals(studentId)) {
                    return true;
                }
            }

        }
        return false;
    }
}
