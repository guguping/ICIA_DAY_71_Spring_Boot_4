package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;
    @GetMapping("/board/save")
    public String boardSave(){
        return "/boardPages/boardSave";
    }
    @PostMapping("/board/save")
    public String saveBoard(@ModelAttribute BoardDTO boardDTO) throws IOException{
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "redirect:/board/";
    }
    @GetMapping("/board/")
    public String boardList(Model model) {
        List<BoardDTO> boardDTO = boardService.findAll();
        model.addAttribute("boardDTO",boardDTO);
        return "/boardPages/boardList";
    }
    @GetMapping("/board/{id}")
    public String boardDetail(@PathVariable Long id,Model model){
        boardService.updateHits(id);
        BoardDTO boardDTO = null;
        try {
            boardDTO = boardService.findById(id);
            model.addAttribute("commentList",commentService.findAll(boardDTO.getId()));
        } catch (NoSuchElementException e) {
            return "boardPages/boardNotFound";
        }
        model.addAttribute("boardDTO",boardDTO);
        return "/boardPages/boardDetail";
    }
    @GetMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable Long id , Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardDTO",boardDTO);
        return "/boardPages/boardUpdate";
    }

    @PostMapping("/board/update")
    public String updateBoard(@ModelAttribute BoardDTO boardDTO) {
        boardService.boardUpdate(boardDTO);
        return "redirect:/board/"+boardDTO.getId();
    }
    @DeleteMapping("/board/delete/{id}")
    private ResponseEntity boardDelete(@PathVariable Long id){
        boardService.boardDelete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("/board/{id}")
    public ResponseEntity update(@RequestBody BoardDTO boardDTO) throws IOException {
        boardService.boardUpdate(boardDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/board/comment")
    public ResponseEntity boardComment(@RequestBody CommentDTO commentDTO) {
        try {
            commentService.commentSave(commentDTO);
            List<CommentDTO> commentDTOList = commentService.findAll(commentDTO.getBoardId());
            return new ResponseEntity<>(commentDTOList,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
