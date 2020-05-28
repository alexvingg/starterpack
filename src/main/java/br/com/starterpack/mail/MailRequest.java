package br.com.starterpack.mail;

import br.com.starterpack.model.User;
import lombok.Data;

import java.util.List;

@Data
public class MailRequest {

    private List<User> users;

    private String subject;

    private String message;
}
