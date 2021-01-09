package com.immortal.internship.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

    void sendMessageWithAttachment(String subject, String content, String...tos);

    void sendSimpleMessage(String subject, String text, String... tos);
}
