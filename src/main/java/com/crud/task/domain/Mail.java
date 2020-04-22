package com.crud.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {

    private String toCc;
    private String mailTo;
    private String Subject;
    private String message;

    public Mail(String amdinMail, String subject, String s) {
        this.mailTo = amdinMail;
        this.Subject = subject;
        this.message = s;
    }
}
