package com.crud.task.controller;

import com.crud.task.domain.CreatedTrelloCardDto;
import com.crud.task.domain.TrelloBoardDto;
import com.crud.task.domain.TrelloCardDto;
import com.crud.task.trello.facade.TrelloFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/trello")
public class TrelloController {

    @Autowired
    private TrelloFacade trelloFacade;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public List<TrelloBoardDto> getTrelloBoards() {
         return trelloFacade.fetchTrelloBoards();
    }

    @RequestMapping(method = RequestMethod.POST, value = "createTrelloCard")
    public CreatedTrelloCardDto createTrelloCard(@RequestBody TrelloCardDto trelloCardDto) {
        return trelloFacade.createCard(trelloCardDto);
    }

    /*@RequestMapping(method = RequestMethod.POST, value = "test")
    public CreatedTrelloCardDto createTrelloCardTest(@RequestParam String name,
                                                     @RequestParam String description,
                                                     @RequestParam String pos,
                                                     @RequestParam String listId) {
        return trelloFacade.createCard(new TrelloCardDto("kodilla", "desc", "1", "5e39d892965b0569ac025a48"));
    }*/
}