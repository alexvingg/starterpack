package br.com.starterpack.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
@Getter
@Setter
public class User extends AbstractModel {

    private String name;

    private String email;

    private String password;

}
