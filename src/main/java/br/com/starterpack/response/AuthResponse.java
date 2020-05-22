package br.com.starterpack.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class AuthResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;
    private final UserResponse userResponse;
}