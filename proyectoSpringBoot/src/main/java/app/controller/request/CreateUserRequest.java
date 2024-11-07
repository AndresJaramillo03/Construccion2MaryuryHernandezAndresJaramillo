package app.controller.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateUserRequest {
    private String name;
    private String document;
    private String number;
    private String username;
    private String password;
    private String userNamePartner;
}
