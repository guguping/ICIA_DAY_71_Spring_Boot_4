package com.example.board.entity;

import com.example.board.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comment_table")
@Getter
@Setter
public class CommentEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(length = 20,nullable = false)
    String commentWriter;
    @Column(length = 200,nullable = false)
    String commentContents;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    BoardEntity boardEntity;

    public static CommentEntity toSaveEntity(BoardEntity boardEntity , CommentDTO commentDTO) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }

}
