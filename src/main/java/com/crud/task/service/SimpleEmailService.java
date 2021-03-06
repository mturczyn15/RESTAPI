package com.crud.task.service;

import com.crud.task.domain.Mail;
import com.crud.task.domain.MailType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private MailCreatorService mailCreatorService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEmailService.class);

    public void send(final Mail mail, MailType type) {
        LOGGER.info("Starting email preparation...");
        try {
            if (type == MailType.DAILY) {
                javaMailSender.send(createDailyMimeMessage(mail));
            } if (type == MailType.TRELLOCARDMAIL) {
                javaMailSender.send(createMimeMessage(mail));
            } else {
                javaMailSender.send(createMailMessage(mail));
            }
            LOGGER.info("Email has been send.");
        } catch (MailException e) {
            LOGGER.error("Failed to process email sending: ", e.getMessage(), e);
        }
    }

    private MimeMessagePreparator createMimeMessage(final Mail mail) {
        return MimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(MimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private MimeMessagePreparator createDailyMimeMessage(final Mail mail) {
        return MimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(MimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildDailyEmail(mail.getMessage()), true);
        };
    }

    private SimpleMailMessage createMailMessage(final Mail mail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(mail.getMailTo());
        mailMessage.setSubject(mail.getSubject());
        mailMessage.setText(mail.getMessage());

        if(StringUtils.isNotBlank(mail.getToCc())) {
            mailMessage.setCc(mail.getToCc());
        }
        return mailMessage;
    }
}
