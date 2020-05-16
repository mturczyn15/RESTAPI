package com.crud.task.service;

import com.crud.task.domain.Mail;
import com.crud.task.domain.MailType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class SimpleEmailServiceTest {

    @InjectMocks
    private SimpleEmailService simpleEmailService;

    @Mock
    private JavaMailSender javaMailSender;

    @Mock
    private MailCreatorService mailCreatorService;

    @Test
    public void shouldSendEmail() {
        //Given
        Mail mail = new Mail("test@test.com", "Test", "Test message");
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo(mail.getMailTo());
        simpleMessage.setSubject(mail.getSubject());
        simpleMessage.setText(mail.getMessage());

        //When
        simpleEmailService.send(mail, MailType.OTHER);

        //Then
        verify(javaMailSender, times(1)).send(simpleMessage);

    }

}