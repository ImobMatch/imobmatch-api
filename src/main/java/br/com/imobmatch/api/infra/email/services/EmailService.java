package br.com.imobmatch.api.infra.email.services;

public interface EmailService {
    void sendEmail(String to, String subject, String text);}
