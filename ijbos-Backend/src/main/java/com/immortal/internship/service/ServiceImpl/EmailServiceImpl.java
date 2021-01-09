package com.immortal.internship.service.ServiceImpl;

import com.immortal.internship.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender emailSender;

    @Override
    public void sendSimpleMessage(String subject, String text,String... tos) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@ijobs.cf");
        message.setTo(tos);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendMessageWithAttachment(String subject, String content, String... tos) {
        MimeMessage message = emailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom("noreply@ijobs.cf");
            helper.setTo(tos);
            helper.setSubject(subject);
            helper.setText("<h1>"+content+"</h1>", true);
            emailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
