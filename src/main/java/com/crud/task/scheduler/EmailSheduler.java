package com.crud.task.scheduler;

import com.crud.task.config.AdminConfig;
import com.crud.task.domain.Mail;
import com.crud.task.repository.TaskRepository;
import com.crud.task.service.SimpleEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailSheduler {

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private TaskRepository taskRepository;

    private static final String SUBJECT = "Tasks: New trello card";

    @Autowired
    private AdminConfig adminConfig;
    @Scheduled(cron = "0 0 10 * * *")
    public  void sendInformationEmail() {
        long size = taskRepository.count();
        simpleEmailService.send(new Mail(
                adminConfig.getAmdinMail(),
                SUBJECT,
                "Currently in database you got: " + size + chooseMessage(size)
        ));
    }

    public static String chooseMessage(long size) {
        return size>=2?" tasks ":" task ";
    }


}
