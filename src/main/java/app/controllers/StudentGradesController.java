package app.controllers;

import app.domain.Grade;
import app.services.GradeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student/grades")
@AllArgsConstructor
public class StudentGradesController {

    private final GradeService gradeService;

    @GetMapping()
    public ResponseEntity<List<Grade>> getMyGrades(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(gradeService.getStudentGrades(userDetails));
    }
}
