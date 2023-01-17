package app.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/videos")
@AllArgsConstructor
public class VideoController {

    @GetMapping("/")
    public ResponseEntity<String> index() {
        return ResponseEntity.ok("You can see the videos!");
    }
}
