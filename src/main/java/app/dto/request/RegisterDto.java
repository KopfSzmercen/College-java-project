package app.dto.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RegisterDto {
    @NotNull(message = "Email is required")
    @Email(message = "Must be a valid email address")
    private String emailAddress;

    @NotNull(message = "First Name is required")
    private String firstName;

    @NotNull(message = "Last Name is required")
    private String lastName;

    @NotNull(message = "Password is required")
    @Length(min = 5, message = "Password must be at least 5 characters")
    private String password;

    @NotNull(message = "Confirm Password is required")
    @Length(min = 5, message = "Confirm Password must be at least 5 characters")
    private String confirmPassword;
}
