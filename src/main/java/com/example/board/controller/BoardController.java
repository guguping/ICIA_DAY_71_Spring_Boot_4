package com.example.board.controller;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.service.BoardService;
import com.example.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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

    @GetMapping("/board")
    public String paging(@PageableDefault(page = 1)Pageable pageable ,
                         @RequestParam(value = "type",required = false,defaultValue = "")String type,
                         @RequestParam(value = "q", required = false , defaultValue = "") String q,
                         Model model){
        //Pageable 는 자바에서 제공하는 인터페이스와 스프링에서 제공하는 인터페이스가 있는데 스프링에서 제공하는
        //Pageable을 사용해야한다
        System.out.println("pageable = " + pageable);
        Page<BoardDTO> boardDTOS = boardService.paging(pageable, type ,q);
        if(boardDTOS.getTotalElements() == 0){
            model.addAttribute("boardList",null);
        } else {
            model.addAttribute("boardList",boardDTOS);
        }
        // 시작페이지(startPage) , 마지막페이지(endPage)값 계산
        // 하단에 보여줄 페이지 갯수 3개
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < boardDTOS.getTotalPages()) ? startPage + blockLimit - 1 : boardDTOS.getTotalPages();
//        if ((startPage + blockLimit - 1) < boardDTOS.getTotalPages()){
//            endPage = startPage + blockLimit -1;
//        }else {
//            endPage = boardDTOS.getTotalPages();
//        }
        // maxPage 값을 계산할 필요가 없는 이유는 boardDTOS.getTotalPages() 메서드가 맥스 페이지 값을 리턴해주기 때문이다
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);
        model.addAttribute("type",type);
        model.addAttribute("q",q);
        return "/boardPages/boardPaging";
    }
    @GetMapping("/board/{id}")
    public String boardDetail(@PathVariable Long id,@RequestParam("page") int page,
                              @RequestParam(value = "type",required = false,defaultValue = "")String type,
                              @RequestParam(value = "q", required = false , defaultValue = "") String q,
                              Model model){
        boardService.updateHits(id);
        model.addAttribute("page",page);
        model.addAttribute("type",type);
        model.addAttribute("q",q);
        try {
            BoardDTO boardDTO = boardService.findById(id);
            List<CommentDTO> commentDTOList = commentService.findAll(boardDTO.getId());
            model.addAttribute("boardDTO",boardDTO);
            if (commentDTOList.size() > 0){
                model.addAttribute("commentList",commentDTOList);
            } else {
                model.addAttribute("commentList",null);
            }
        } catch (NoSuchElementException e) {
            return "boardPages/boardNotFound";
        }
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
            System.out.println("commentDTOList = " + commentDTOList);
            return new ResponseEntity<>(commentDTOList,HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
