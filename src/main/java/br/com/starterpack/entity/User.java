package br.com.starterpack.entity;

import br.com.starterpack.core.entity.AbstractEntity;
import br.com.starterpack.core.validation.OnCreate;
import br.com.starterpack.core.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.util.List;

@Document("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity {

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    private String name;

    @Indexed(unique = true)
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @Indexed(unique = true)
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Email(groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @NotEmpty(groups = {OnCreate.class})
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Transient
    private String confirmPassword;

    private String image;

    @NotEmpty(groups = {OnCreate.class, OnUpdate.class})
    private List<String> roles;

}
