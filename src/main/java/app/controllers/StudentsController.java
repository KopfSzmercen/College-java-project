package app.controllers;

import app.dto.request.RegisterStudentDto;
import app.dto.response.StudentBasicInfoResponse;
import app.dto.response.StudentDetailsResponse;
import app.exceptions.BadRequestException;
import app.services.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/v1/students")
@AllArgsConstructor
public class StudentsController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<String> createStudent(@Valid @RequestBody RegisterStudentDto registerDto, @AuthenticationPrincipal UserDetails userDetails) {
        studentService.registerStudent(registerDto, userDetails);
        return ResponseEntity.ok("Success");
    }

    @GetMapping
    public ResponseEntity<List<StudentBasicInfoResponse>> getStudents(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(studentService.getStudents(userDetails));
    }

    @GetMapping("{studentId}")
    public ResponseEntity<StudentDetailsResponse> getStudentById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long studentId) {
        if (studentId == null) throw new BadRequestException("Invalid param");
        var student = studentService.getStudentById(userDetails, studentId);
        if (student == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return ResponseEntity.ok(student);
    }
}
