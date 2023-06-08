package com.example.board.dto;

import com.example.board.entity.BaseEntity;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import com.example.board.uil.UtilClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    String createdAt;
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
        boardDTO.setCreatedAt(UtilClass.dateFormat(boardEntity.getCreatedAt()));

        //파일 여부에 따른 코드 추가
        if (boardEntity.getFileAttached() == 1){
            boardDTO.setFileAttached(1);
            // 파일 이름을 담을 리스트 객체 선언
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList= new ArrayList<>();
            //첨부파일에 각각 접근
            for (BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()){
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);
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
