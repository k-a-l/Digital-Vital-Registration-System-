package com.kalyan.smartmunicipality.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    // ✅ Send HTML email (without attachment)
    public void sendEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML content
            mailSender.send(mimeMessage);

            log.info("HTML email sent to: {}, Subject: {}", to, subject);
        } catch (MessagingException e) {
            log.error("Failed to send HTML email to {}: {}", to, e.getMessage());
        }
    }

    // ✅ Send HTML email with a PDF attachment
    public void sendEmailWithAttachment(String to, String subject, String htmlContent, byte[] attachmentData) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // true = HTML content

            // Attach byte[] as a file
            ByteArrayResource attachment = new ByteArrayResource(attachmentData);
            helper.addAttachment("certificate.pdf", attachment);

            mailSender.send(mimeMessage);

            log.info("HTML email with attachment sent to: {}", to);
        } catch (MessagingException e) {
            log.error("Failed to send HTML email with attachment to {}: {}", to, e.getMessage());
            throw new RuntimeException("Failed to send HTML email with attachment", e);
        }
    }
}
