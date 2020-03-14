package com.crud.task.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Mail {

    private String toCc;
    private String mailTo;
    private String Subject;
    private String message;
}
