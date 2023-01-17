package app.services;

import app.domain.Grade;
import app.domain.UserRole;
import app.dto.request.CreateGradeDto;
import app.dto.request.EditGradeDto;
import app.exceptions.ForbiddenException;
import app.exceptions.NotFoundException;
import app.repositories.ApplicationUserRepository;
import app.repositories.GradeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GradeService {
    private final ApplicationUserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final ServicesUtil servicesUtil;

    public void createGrade(CreateGradeDto createGradeDto, UserDetails userDetails) {
        var user = userRepository.findByEmailAddress(userDetails.getUsername()).orElseThrow();
        if (user.getRole() != UserRole.TEACHER)
            throw new ForbiddenException("You can't create a grade if you're not a teacher");

        boolean isStudentInAnyTeacherClassroom = servicesUtil.isStudentInAnyOfTeacherClassrooms(user, createGradeDto.getStudentId());
        if (!isStudentInAnyTeacherClassroom) throw new ForbiddenException("Student does not exist in your classrooms");

        var grade = new Grade();
        grade.setDescription(createGradeDto.getDescription());
        grade.setTeacherId(user.getId());
        grade.setStudentId(createGradeDto.getStudentId());
        grade.setSubject(createGradeDto.getSubject());
        grade.setLevel(createGradeDto.getGradeLevel());

        gradeRepository.save(grade);
    }

    public List<Grade> getStudentGrades(UserDetails userDetails) {
        var user = userRepository.findByEmailAddress(userDetails.getUsername()).orElseThrow();
        if (user.getRole() != UserRole.STUDENT)
            throw new ForbiddenException("You can't see your own notes if you're not a student.");
        return user.getStudentGrades();
    }

    public void editGrade(UserDetails userDetails, EditGradeDto editGradeDto, Long gradeId) {
        var user = userRepository.findByEmailAddress(userDetails.getUsername()).orElseThrow();
        if (user.getRole() != UserRole.TEACHER)
            throw new ForbiddenException("You can't edit a grade if you're not a teacher");

        var grade = gradeRepository.findById(gradeId).orElseThrow(() -> new NotFoundException("Grade not found"));
        if (grade.getTeacherId() != user.getId()) throw new ForbiddenException("You can't edit this note");

        if (editGradeDto.getGradeLevel() != null) grade.setLevel(editGradeDto.getGradeLevel());
        if (editGradeDto.getDescription() != null) grade.setDescription(editGradeDto.getDescription());
        if (editGradeDto.getSubject() != null) grade.setSubject(editGradeDto.getSubject());
        if (editGradeDto.getStudentId() != null) {
            boolean isStudentInAnyTeacherClassroom = servicesUtil.isStudentInAnyOfTeacherClassrooms(user, editGradeDto.getStudentId());
            if (!isStudentInAnyTeacherClassroom)
                throw new ForbiddenException("Student does not exist in your classrooms");
            grade.setStudentId(editGradeDto.getStudentId());
        }

        gradeRepository.save(grade);
    }

    public void deleteGrade(UserDetails userDetails, Long gradeId) {
        var user = userRepository.findByEmailAddress(userDetails.getUsername()).orElseThrow();
        if (user.getRole() != UserRole.TEACHER)
            throw new ForbiddenException("You can't edit a grade if you're not a teacher");

        var grade = gradeRepository.findById(gradeId).orElseThrow(() -> new NotFoundException("Grade not found"));
        if (grade.getTeacherId() != user.getId()) throw new ForbiddenException("You can't edit this note");

        gradeRepository.deleteById(gradeId);
    }
}
