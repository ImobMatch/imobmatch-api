package br.com.imobmatch.api.infra.email.services;

import br.com.imobmatch.api.exceptions.email.ErroSendEmailException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void sendValidationEmail(String to, String code) {
        try {
            ClassPathResource resource = new ClassPathResource("validate-email.html");
            String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            html = html.replace("{{CODIGO_VALIDACAO}}", code);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Código de Validação - Imobmatch");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new ErroSendEmailException();
        }
    }

    @Override
    public void sendValidationEmailForResetPassword(String to, String code) {
        try {
            ClassPathResource resource = new ClassPathResource("reset-password.html");
            String html = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

            html = html.replace("{{CODIGO_VALIDACAO}}", code);

            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject("Código de Validação de Email - Imobmatch");
            helper.setText(html, true);

            mailSender.send(message);

        } catch (Exception e) {
            throw new ErroSendEmailException();
        }
    }
}
