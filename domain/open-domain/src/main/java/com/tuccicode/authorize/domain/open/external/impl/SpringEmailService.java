package com.tuccicode.authorize.domain.open.external.impl;

import com.tuccicode.authorize.domain.open.external.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

/**
 * @author tucci.lee
 */
@Service
public class SpringEmailService implements EmailService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final JavaMailSenderImpl mailSender;

    public SpringEmailService(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void send(String email, String subject, String text) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(mailSender.getUsername());
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(text, Boolean.TRUE);
            mailSender.send(mimeMessage);
            logger.info("发送邮件: {}, 主题: {}", email, subject);
        } catch (Exception e) {
            logger.error("发送邮件失败", e);
        }
    }
}
