package com.example.board.dto;

import com.example.board.entity.CommentEntity;
import com.example.board.uil.UtilClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDTO {
    Long id;
    String commentWriter;
    String commentContents;
    Long boardId;
    String createdAt;
    String UpdateAt;

    public static CommentDTO toDTO(CommentEntity commentEntity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setBoardId(commentEntity.getBoardEntity().getId());
        commentDTO.setCreatedAt(UtilClass.dateFormat(commentEntity.getCreatedAt()));
        commentDTO.setUpdateAt(UtilClass.dateFormat(commentEntity.getUpdatedAt()));
        return commentDTO;
    }
}
