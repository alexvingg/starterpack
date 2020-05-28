package br.com.starterpack.core.mail;

import br.com.starterpack.exception.StarterPackException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@NoArgsConstructor
public class GenericMailProcessor implements IMailProcessor {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private Environment env;

    @Override
    public void process(Mail mail) throws Exception {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mail.getFrom().orElse(this.env.getProperty("mail.from")));
        simpleMailMessage.setTo(mail.getTo().toArray(String[]::new));
        simpleMailMessage.setSubject(mail.getSubject());
        simpleMailMessage.setText(mail.getBody());

        MimeMessage message = this.emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        mail.getAttachments().orElse(List.of()).forEach(file -> {
            try {
                helper.addAttachment("", file);
            } catch (MessagingException e) {
                throw new StarterPackException("Erro ao processar anexo de email");
            }
        });

        this.emailSender.send(simpleMailMessage);
    }
}
