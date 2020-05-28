package br.com.starterpack.core.mail;

import br.com.starterpack.entity.User;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class MailRequest {

    @NotEmpty
    private List<User> users;

    @NotEmpty
    private String subject;

    @NotEmpty
    private String message;
}
