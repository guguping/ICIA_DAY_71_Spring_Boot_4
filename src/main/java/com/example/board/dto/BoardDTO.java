package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDTO {
    Long id;
    String boardWriter;
    String boardTitle;
    String boardPass;
    String boardContents;
    int boardHits = 0;
    LocalDateTime createdAt;
    int fileAttached =0;
    List<MultipartFile> boardFile;
    List<String> originalFileName;
    List<String> storedFileName;

    public static BoardDTO toDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setCreatedAt(boardEntity.getCreatedAt());

        //파일 여부에 따른 코드 추가
        if (boardEntity.getFileAttached() == 1){
            boardDTO.setFileAttached(1);
            for (BoardFileEntity board : boardEntity.getBoardFileEntityList()){
                boardDTO.setOriginalFileName(Collections.singletonList(board.getOriginalFileName()));
                boardDTO.setStoredFileName(Collections.singletonList(board.getStoredFileName()));
            }
        } else {
            boardDTO.setFileAttached(0);
        }
        return boardDTO;

        //빌더 패턴
//        return BoardDTO.builder()
//                .id(boardEntity.getId())
//                .boardWriter(boardEntity.getBoardWriter())
//                .boardPass(boardEntity.getBoardPass())
//                .boardTitle(boardEntity.getBoardTitle())
//                .boardContents(boardEntity.getBoardContents())
//                .boardHits(boardEntity.getBoardHits())
//                .createdAt(boardEntity.getCreatedAt())
//                .build();
    }
}
