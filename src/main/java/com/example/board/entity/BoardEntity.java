package com.example.board.entity;

import com.example.board.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.security.auth.callback.LanguageCallback;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_table")
@Getter
@Setter
public class BoardEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(length = 20,nullable = false)
    String boardWriter;
    @Column(length = 50,nullable = false)
    String boardTitle;
    @Column(length = 20,nullable = false)
    String boardPass;
    @Column(length = 500)
    String boardContents;
    @Column
    int boardHits;
    @Column
    int fileAttached;

    @OneToMany(mappedBy = "boardEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    List<BoardFileEntity> boardFileEntityList = new ArrayList<>();
    @OneToMany(mappedBy = "boardEntity",cascade = CascadeType.REMOVE,orphanRemoval = true,fetch = FetchType.LAZY)
    List<CommentEntity> commentEntityList = new ArrayList<>();

    public static BoardEntity toSaveEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setCreatedAt(boardEntity.getCreatedAt());
        boardEntity.setFileAttached(0);
        return boardEntity;
    }

    public static BoardEntity toUpdate(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId());
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setCreatedAt(boardEntity.getUpdatedAt());
        boardEntity.setFileAttached(boardDTO.getFileAttached());
        return boardEntity;
    }

    public static BoardEntity toSaveEntityWithFile(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardHits(boardDTO.getBoardHits());
        boardEntity.setFileAttached(1);
        return boardEntity;
    }
}
