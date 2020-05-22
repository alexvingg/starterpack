package br.com.starterpack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractModel {

    private String username;

    private String email;

    private String password;

    @Transient
    @JsonIgnore
    private String confirmPassword;

    private String image;

    private List<String> roles;

}
