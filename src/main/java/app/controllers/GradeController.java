package app.controllers;

import app.dto.request.CreateGradeDto;
import app.dto.request.EditGradeDto;
import app.exceptions.BadRequestException;
import app.services.GradeService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/grades")
@AllArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PostMapping
    public ResponseEntity<String> createGrade(@Valid @RequestBody CreateGradeDto createGradeDto, @AuthenticationPrincipal UserDetails userDetails) {
        gradeService.createGrade(createGradeDto, userDetails);
        return ResponseEntity.ok("Success");
    }


    @PutMapping("{gradeId}")
    public ResponseEntity<String> editGrade(@Valid @RequestBody EditGradeDto editGradeDto, @AuthenticationPrincipal UserDetails userDetails, @PathVariable Long gradeId) {
        if (gradeId == null) throw new BadRequestException("Invalid param");
        gradeService.editGrade(userDetails, editGradeDto, gradeId);
        return ResponseEntity.ok("Success");
    }

    @DeleteMapping("{gradeId}")
    public ResponseEntity<String> deleteGrade(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long gradeId) {
        if (gradeId == null) throw new BadRequestException("Invalid param");

        gradeService.deleteGrade(userDetails, gradeId);
        return ResponseEntity.ok("Success");
    }
}
