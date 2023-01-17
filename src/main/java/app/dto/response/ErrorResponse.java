package app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private Map<String, String> validationErrors;
    private HttpStatus status;
}

