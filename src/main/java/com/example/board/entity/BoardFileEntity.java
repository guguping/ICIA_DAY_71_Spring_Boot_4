package com.example.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "board_file_table")
@Getter
@Setter
public class BoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 100)
    String originalFileName;
    @Column(length = 100)
    String storedFileName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")// 실제 DB에 생성되는 컬럼의 이름
    BoardEntity boardEntity; // 부모 엔티티 타입으로 정의
    // @ManyToOne(fetch = FetchType.EAGER)
    // 부모 데이터를 호출하면 무조건 함께 가져감
    // @ManyToOne(fetch = FetchType.LAZY)
    // 부모 데이터를 호출해도 필요할때만 가져감 (보통 LAZY 로 많이 사용함)

    public static BoardFileEntity toSaveBoardFileEntity(BoardEntity savedEntity , String originalFileName,String storedFileName){
        BoardFileEntity boardFileEntity = new BoardFileEntity();
        boardFileEntity.setBoardEntity(savedEntity);
        boardFileEntity.setOriginalFileName(originalFileName);
        boardFileEntity.setStoredFileName(storedFileName);
        return boardFileEntity;
    }

}
