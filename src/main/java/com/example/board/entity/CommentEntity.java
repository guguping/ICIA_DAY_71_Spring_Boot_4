package com.example.board.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "comment_table")
@Getter
@Setter
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(length = 20,nullable = false)
    String commentWriter;
    @Column(length = 200,nullable = false)
    String commentContents;
    @Column
    Long boardId;

}
