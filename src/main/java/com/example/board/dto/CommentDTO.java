package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentDTO {
    Long id;
    String commentWriter;
    String commentContents;
    Long boardId;
}
