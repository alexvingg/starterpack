package br.com.starterpack.response;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@EqualsAndHashCode
public class UserResponse {

    private String username;

    private String email;

    private String password;

    private List<String> roles;
}
