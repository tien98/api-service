package com.service.apiservice.service;

import com.service.apiservice.dto.ContactMailDTO;
import com.service.apiservice.dto.EmailDetailsDTO;
import com.service.apiservice.dto.FCMRequestDTO;

public interface EmailService {
    String sendMailContact(ContactMailDTO details);
    // Method
    // To send a simple email
    String sendSimpleMail(EmailDetailsDTO details);

    // Method
    // To send an email with attachment
    String sendMailWithAttachment(EmailDetailsDTO details);

    Object sendFCM(FCMRequestDTO request);
}
