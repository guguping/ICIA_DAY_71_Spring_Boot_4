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
    @Column
    Long boardId;

}
