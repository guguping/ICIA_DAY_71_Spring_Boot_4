package com.example.board.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardFileDTO {
    Long id;
    String originalFileName;
    String storedFileName;
    Long boardId;
}
