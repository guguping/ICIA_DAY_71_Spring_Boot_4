package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        if (boardDTO.getBoardFile().get(0).isEmpty()) {
            boardRepository.save(BoardEntity.toSaveEntity(boardDTO));
        }
    }

    public List<BoardDTO> findAll() {
        List<BoardDTO> boardDTOList = new ArrayList<>();
//        for (BoardEntity boardEntity : boardRepository.findAll()) {
//            boardDTOList.add(BoardDTO.toDTO(boardEntity));
//        }
        boardRepository.findAll().forEach(boardEntity -> {
            boardDTOList.add(BoardDTO.toDTO(boardEntity));
        });
        return boardDTOList;
    }
    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }
    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return BoardDTO.toDTO(boardEntity);
    }

    public void boardUpdate(BoardDTO boardDTO) {
        boardRepository.save(BoardEntity.toUpdate(boardDTO));
    }

    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }
}
