package com.crud.task.service;

import com.crud.task.config.AdminConfig;
import com.crud.task.domain.*;
import com.crud.task.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Optional.ofNullable;


@Service
public class TrelloService {

    @Autowired
    private TrelloClient trelloClient;

    @Autowired
    private SimpleEmailService simpleEmailService;

    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "Tasks: New trello card";

    public List<TrelloBoardDto> fetchTrelloBoards() {
        return trelloClient.getTrelloBoards();
    }

    public CreatedTrelloCardDto createTrelloCard(final TrelloCardDto trelloCardDto) {
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(newCard).ifPresent(card -> simpleEmailService.send(new Mail(
                adminConfig.getAmdinMail(),
                SUBJECT,
                "New card: " + trelloCardDto.getName() + " has been created on your Trello account"), MailType.TRELLOCARDMAIL));

        return newCard;
    }
}
