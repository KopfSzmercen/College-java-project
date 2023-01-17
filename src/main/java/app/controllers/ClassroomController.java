package app.controllers;

import app.domain.Classroom;
import app.dto.request.CreateClassroomDto;
import app.dto.response.ClassroomBasicInfoResponse;
import app.exceptions.BadRequestException;
import app.services.AuthService;
import app.services.ClassroomService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/classrooms")
@AllArgsConstructor
public class ClassroomController {

    private final ClassroomService classroomService;
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<String> createClassroom(@Valid @RequestBody CreateClassroomDto createClassroomDto, @AuthenticationPrincipal UserDetails userDetails) {
        classroomService.createClassroom(createClassroomDto, userDetails);
        return ResponseEntity.ok("Success");
    }

    @GetMapping
    public ResponseEntity<List<ClassroomBasicInfoResponse>> getClassrooms(@AuthenticationPrincipal UserDetails userDetails) {
        var classrooms = classroomService.getClassrooms(userDetails);
        return ResponseEntity.ok(classrooms);
    }

    @GetMapping("{classroomId}")
    public ResponseEntity<Classroom> getClassroomById(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long classroomId) {
        if (classroomId == null) throw new BadRequestException("Invalid param");

        var classroom = classroomService.getClassroomById(userDetails, classroomId);
        if (classroom == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(classroom);
    }
}
