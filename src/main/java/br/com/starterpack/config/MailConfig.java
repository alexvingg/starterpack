package br.com.starterpack.config;

import br.com.starterpack.mail.GenericMailProcessor;
import br.com.starterpack.mail.IMailProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Autowired
    private Environment env;

    @Bean
    public JavaMailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        mailSender.setHost(env.getProperty("mail.smtp.host"));
        mailSender.setPort(env.getProperty("mail.smtp.port", Integer.class));

        mailSender.setUsername(this.env.getProperty("MAIL_SMTP_USERNAME", env.getProperty("mail.smtp.username")));
        mailSender.setPassword(this.env.getProperty("MAIL_SMTP_PASSWORD", env.getProperty("mail.smtp.password")));

        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.starttls.enable", true);
        props.put("mail.smtp.connectiontimeout", 10000);

        mailSender.setJavaMailProperties(props);

        return mailSender;
    }

    @Bean
    public IMailProcessor mailProcessor(){
        return new GenericMailProcessor();
    }
}
