package app.dto.response;

import app.domain.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginSuccess {
    private String message;
    private String accessToken;

    private UserRole role;
}
