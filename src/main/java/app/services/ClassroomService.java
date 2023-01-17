package app.services;

import app.domain.Classroom;
import app.domain.UserRole;
import app.dto.request.CreateClassroomDto;
import app.dto.response.ClassroomBasicInfoResponse;
import app.exceptions.ForbiddenException;
import app.mappers.ClassroomBasicInfoMapper;
import app.repositories.ApplicationUserRepository;
import app.repositories.ClassroomRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class ClassroomService {
    private final ApplicationUserRepository applicationUserRepository;
    private final ClassroomRepository classroomRepository;

    private final ClassroomBasicInfoMapper classroomBasicInfoMapper;

    public void createClassroom(CreateClassroomDto createClassroomDto, UserDetails userDetails) throws RuntimeException {
        var userEmail = userDetails.getUsername();

        var user = applicationUserRepository.findByEmailAddress(userEmail).orElseThrow();

        if (user.getRole() != UserRole.TEACHER)
            throw new ForbiddenException("You can't create a classroom if you're not a teacher");

        var classroom = new Classroom();
        classroom.setTeacherId(user.getId());
        classroom.setName(createClassroomDto.getClassroomName());

        classroomRepository.save(classroom);
    }

    public List<ClassroomBasicInfoResponse> getClassrooms(UserDetails userDetails) {
        var userEmail = userDetails.getUsername();
        var user = applicationUserRepository.findByEmailAddress(userEmail).orElseThrow();
        var classrooms = classroomRepository.findManyByTeacherId(user.getId());
        return classroomBasicInfoMapper.toDtos(classrooms);
    }

    public Classroom getClassroomById(UserDetails userDetails, Long classroomId) {
        var userEmail = userDetails.getUsername();
        var user = applicationUserRepository.findByEmailAddress(userEmail).orElseThrow();
        if (user.getRole() != UserRole.TEACHER)
            throw new ForbiddenException("You can't see a classroom if you're not a teacher.");

        boolean isClassroomIdInTeachersClassroomsList = user.getOwnedClassrooms().stream().anyMatch(x -> x.getId().equals(classroomId));
        if (!isClassroomIdInTeachersClassroomsList)
            throw new ForbiddenException("You can't add students to a classroom you don't own");

        return classroomRepository.getById(classroomId);
    }

}
