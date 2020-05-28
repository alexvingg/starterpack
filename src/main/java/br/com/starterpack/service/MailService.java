package br.com.starterpack.service;

import br.com.starterpack.exception.BusinessException;
import br.com.starterpack.mail.IMailProcessor;
import br.com.starterpack.mail.Mail;
import br.com.starterpack.mail.MailRequest;
import br.com.starterpack.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class MailService {

    @Autowired
    private IMailProcessor iMailProcessor;

    public void sendMail(MailRequest mailRequest) {

        Mail mail = Mail.builder().to(mailRequest.getUsers().stream().map(User::getEmail)
                .collect(Collectors.toList()))
                .body(mailRequest.getMessage())
                .subject(mailRequest.getSubject())
                .build();

        try {
            this.iMailProcessor.process(mail);
        }catch (Exception e) {
            throw new BusinessException("Erro ao enviar email");
        }
    }
}
