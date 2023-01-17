package app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class ApiResponse {
    private HttpStatus status;
    private String message;

    public static ApiResponse success(String message) {
        return new ApiResponse(HttpStatus.OK, message);
    }

    public static ApiResponse forbidden(String message) {
        return new ApiResponse(HttpStatus.FORBIDDEN, message);
    }

    public static ApiResponse badRequest(String message) {
        return new ApiResponse(HttpStatus.BAD_REQUEST, message);
    }

    public static ApiResponse unauthorized(String message) {
        return new ApiResponse(HttpStatus.UNAUTHORIZED, message);
    }

    public static ApiResponse error(String message) {
        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
