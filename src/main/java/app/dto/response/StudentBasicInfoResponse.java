package app.dto.response;

import lombok.Data;

@Data
public class StudentBasicInfoResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private String emailAddress;


    public StudentBasicInfoResponse(Long id, String firstName, String lastName, String emailAddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;

    }
}
