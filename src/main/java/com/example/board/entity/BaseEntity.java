package com.example.board.entity;

import com.example.board.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class BaseEntity {
    // 생성 시간을 관리하는 컬럼
    // update false
    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    //업데이트 시간을 관리하는 컬럼
    // insert false
    @UpdateTimestamp
    @Column(insertable = false)
    LocalDateTime updatedAt;


}
