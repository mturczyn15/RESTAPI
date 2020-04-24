package com.crud.task.mapper;

import com.crud.task.domain.*;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FacadeMapperTestSuite {

    @Autowired
    private TrelloMapper trelloMapper;
    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        TrelloListDto trelloListDto1 = new TrelloListDto("12", "lists", false);
        TrelloListDto trelloListDto2 = new TrelloListDto("12", "lists", false);
        TrelloListDto trelloListDto3 = new TrelloListDto("12", "lists", false);
        TrelloListDto trelloListDto4 = new TrelloListDto("12", "lists", false);
        trelloListDto.add(trelloListDto1);
        trelloListDto.add(trelloListDto2);
        trelloListDto.add(trelloListDto3);
        trelloListDto.add(trelloListDto4);
        //When
        List<TrelloList> lists = trelloMapper.mapToList(trelloListDto);
        //Then
        Assert.assertEquals("12", lists.get(0).getId());
        Assert.assertEquals("lists", lists.get(0).getName());
        Assert.assertFalse(lists.get(0).isClosed());
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        TrelloList trelloList1 = new TrelloList("12", "lists", false);
        TrelloList trelloList2 = new TrelloList("12", "lists", false);
        TrelloList trelloList3 = new TrelloList("12", "lists", false);
        TrelloList trelloList4 = new TrelloList("12", "lists", false);
        trelloList.add(trelloList1);
        trelloList.add(trelloList2);
        trelloList.add(trelloList3);
        trelloList.add(trelloList4);
        //When
        List<TrelloListDto> lists = trelloMapper.mapToListDto(trelloList);
        //Then
        Assert.assertEquals("12", lists.get(0).getId());
        Assert.assertEquals("lists", lists.get(0).getName());
        Assert.assertFalse(lists.get(0).isClosed());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("card", "des", "1", "1234");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        Assert.assertEquals("card", trelloCardDto.getName());
        Assert.assertEquals("des", trelloCardDto.getDescription());
        Assert.assertEquals("1", trelloCardDto.getPos());
        Assert.assertEquals("1234", trelloCardDto.getListId());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("card", "des", "1", "1234");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        Assert.assertEquals("card", trelloCard.getName());
        Assert.assertEquals("des", trelloCard.getDescription());
        Assert.assertEquals("1", trelloCard.getPos());
        Assert.assertEquals("1234", trelloCard.getListId());
    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        TrelloBoard board = new TrelloBoard("12", "board", new ArrayList<>());
        TrelloBoard board1 = new TrelloBoard("12", "board", new ArrayList<>());
        TrelloBoard board2 = new TrelloBoard("12", "board", new ArrayList<>());
        List<TrelloBoard> boards = new ArrayList<>();
        boards.add(board);
        boards.add(board1);
        boards.add(board2);
        //When
        List<TrelloBoardDto> boardsDto = trelloMapper.mapToBoardsDto(boards);
        //Then
        Assert.assertEquals("12", boardsDto.get(0).getId());
        Assert.assertEquals("board", boardsDto.get(0).getName());
        Assert.assertTrue(boardsDto.get(0).getLists().isEmpty());

    }

    @Test
    public void testMapToBoards() {
        //Given
        TrelloBoardDto boardDto = new TrelloBoardDto("12", "board", new ArrayList<>());
        TrelloBoardDto boardDto1 = new TrelloBoardDto("12", "board", new ArrayList<>());
        TrelloBoardDto boardDto2 = new TrelloBoardDto("12", "board", new ArrayList<>());
        List<TrelloBoardDto> boardsDto = new ArrayList<>();
        boardsDto.add(boardDto);
        boardsDto.add(boardDto1);
        boardsDto.add(boardDto2);
        //When
        List<TrelloBoard> boards = trelloMapper.mapToBoards(boardsDto);
        //Then
        Assert.assertEquals("12", boards.get(0).getId());
        Assert.assertEquals("board", boards.get(0).getName());
        Assert.assertTrue(boards.get(0).getLists().isEmpty());

    }
}
