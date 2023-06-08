package com.example.board.service;

import com.example.board.dto.CommentDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.CommentEntity;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long commentSave(CommentDTO commentDTO) {
        BoardEntity boardEntity = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(()->new NoSuchElementException());
        CommentEntity commentEntity = CommentEntity.toSaveEntity(boardEntity,commentDTO);
        return commentRepository.save(commentEntity).getId();
    }

    @Transactional
    public List<CommentDTO> findAll(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(()-> new NoSuchElementException());
        //1.BoardEntity에서 댓글 목록 가져오기
//        List<CommentEntity> commentEntityList = boardEntity.getCommentEntityList();
        //2.CommentRepository에서 가져오기
        // select * from comment_table where boardId = ?
        List<CommentEntity> commentEntityList = commentRepository.findByBoardEntityOrderByIdDesc(boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentEntityList.forEach(Comment -> {
            commentDTOList.add(CommentDTO.toDTO(Comment));
        });
        return commentDTOList;
    }
}
