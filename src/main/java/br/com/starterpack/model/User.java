package br.com.starterpack.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

@Document("user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractModel {

    private String username;

    private String email;

    private String password;

    private List<String> roles;

}
