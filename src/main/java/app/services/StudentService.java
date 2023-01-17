package app.services;

import app.domain.ApplicationUser;
import app.domain.UserRole;
import app.dto.request.RegisterStudentDto;
import app.dto.response.StudentBasicInfoResponse;
import app.dto.response.StudentDetailsResponse;
import app.exceptions.BadRequestException;
import app.exceptions.ForbiddenException;
import app.mappers.StudentDetailInfoMapper;
import app.repositories.ApplicationUserRepository;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class StudentService {
    private final ApplicationUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final StudentDetailInfoMapper studentDetailInfoMapper;

    private final ServicesUtil servicesUtil;

    public void registerStudent(RegisterStudentDto registrationDto, UserDetails userDetails) throws RuntimeException {
        var user = userRepository.findByEmailAddress(userDetails.getUsername()).orElseThrow();

        if (user.getRole() != UserRole.TEACHER)
            throw new ForbiddenException("You can't create a student if you're not a teacher");

        var classrooms = user.getOwnedClassrooms();
        boolean isClassroomIdInTeachersClassroomsList = classrooms.stream().anyMatch(x -> x.getId().equals(registrationDto.getClassroomId()));

        if (!isClassroomIdInTeachersClassroomsList)
            throw new BadRequestException("You can't add students to a classroom you don't own");


        Optional<app.domain.ApplicationUser> emailUser = userRepository.findByEmailAddress(registrationDto.getEmailAddress());

        if (emailUser.isPresent()) {
            throw new BadRequestException("Email Address already in use.");
        }

        var studentUser = new ApplicationUser();
        studentUser.setEmailAddress(registrationDto.getEmailAddress());
        studentUser.setFirstName(registrationDto.getFirstName());
        studentUser.setLastName(registrationDto.getLastName());
        studentUser.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        studentUser.setRegistrationDate(LocalDate.now());
        studentUser.setRole(UserRole.STUDENT);
        studentUser.setClassroomId(registrationDto.getClassroomId());

        userRepository.save(studentUser);
    }

    public StudentDetailsResponse getStudentById(@NotNull UserDetails userDetails, long studentId) throws RuntimeException {
        var user = userRepository.findByEmailAddress(userDetails.getUsername()).orElseThrow();
        if (user.getRole() != UserRole.TEACHER)
            throw new ForbiddenException("You can't get a student if you're not a teacher");

        var isStudentInAnyOfTeacherClassrooms = servicesUtil.isStudentInAnyOfTeacherClassrooms(user, studentId);
        if (!isStudentInAnyOfTeacherClassrooms)
            throw new ForbiddenException("You can't see details of a student who doesn't exist in any of your classrooms.");

        var student = userRepository.findById(studentId).orElseThrow();
        return studentDetailInfoMapper.toDto(student);
    }

    public List<StudentBasicInfoResponse> getStudents(@NotNull UserDetails userDetails) {
        var user = userRepository.findByEmailAddress(userDetails.getUsername()).orElseThrow();
        if (user.getRole() != UserRole.TEACHER)
            throw new ForbiddenException("You can't get students if you're not a teacher");
        return studentDetailInfoMapper.toDtos(user);

    }

}
