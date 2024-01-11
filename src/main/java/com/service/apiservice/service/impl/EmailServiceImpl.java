package com.service.apiservice.service.impl;

import com.service.apiservice.config.ResponseDataConfiguration;
import com.service.apiservice.dto.ContactMailDTO;
import com.service.apiservice.dto.EmailDetailsDTO;
import com.service.apiservice.dto.FCMRequestDTO;
import com.service.apiservice.service.EmailService;
import com.service.apiservice.utils.RestApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.File;

@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    RestApiUtils restApiUtils;

    @Value("${spring.mail.username}") private String sender;

    public String sendMailContact(ContactMailDTO details)
    {
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(details.getFrom());
            if(details.getTo().isEmpty()) {
                mailMessage.setTo(sender);
            }else {
                mailMessage.setTo(details.getTo());
            }
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String sendSimpleMail(EmailDetailsDTO details)
    {
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "";
        }

        // Catch block to handle the exceptions
        catch (Exception e) {
            return e.getMessage();
        }
    }

    public String sendMailWithAttachment(EmailDetailsDTO details)
    {
        // Creating a mime message
        MimeMessage mimeMessage
                = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;

        try {

            // Setting multipart as true for attachments to
            // be send
            mimeMessageHelper
                    = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(
                    details.getSubject());

            // Adding the attachment
            FileSystemResource file
                    = new FileSystemResource(
                    new File(details.getAttachment()));

            mimeMessageHelper.addAttachment(
                    file.getFilename(), file);

            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "";
        }

        // Catch block to handle MessagingException
        catch (MessagingException e) {

            // Display message when exception occurred
            return e.getMessage();
        }
    }

    @Override
    public Object sendFCM(FCMRequestDTO request) {
        String url = "https://fcm.googleapis.com/fcm/send";
        Object status = restApiUtils.postBodyObject(url, request, Object.class, false);
        return ResponseDataConfiguration.success(status);
    }
}
