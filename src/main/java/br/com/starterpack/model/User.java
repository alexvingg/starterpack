package br.com.starterpack.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private List<String> roles;

}
