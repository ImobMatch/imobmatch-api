package br.com.imobmatch.api.infra.email.services;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
    void sendValidationEmail(String to, String code);
    void sendValidationEmailForResetPassword(String to, String code);
}
