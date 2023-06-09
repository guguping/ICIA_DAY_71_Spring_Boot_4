package com.example.board.repository;

import com.example.board.entity.BoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity,Long> {
    // update board_table set board_hits= board_hits+1 where id = ?
    // jpql(java persistence query language): 필요한 쿼리문을 직접 적용하고자 할 때 사용
    @Modifying
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id")
    // 테이블 이름의 자리에는 Entity의 이름을 써주어야하고 약어(별칭)로 사용해야한다 (보통 이렇게 사용한다)
//    @Query(value = "update board_table set boardHits=boardHits+1 where id=:id",nativeQuery = true)
//    실제 쿼리문 처럼 쓰고 싶다면 뒤에 nativeQuery(쿼리 원문)를 true로 설정해줘야 한다 디폴트가 false이기 떄문
    void updateHits(@Param("id") Long id);
    // where절의 id를 변수 처리하고 param으로 매개변수를 날려준다

    //제목으로
    // selcet * from board_table where boardContents like '%q%' or boardTitle like '%q%';
    List<BoardEntity> findByBoardTitleContaining(String q);

    //작성자로 검색
    List<BoardEntity> findByBoardWriterContaining(String q);
    Page<BoardEntity> findByBoardWriterContaining(String q, Pageable pageable);

    Page<BoardEntity> findByBoardTitleContaining(String q, Pageable pageable);

    //제목으로 검색한 결과를 id 기준 내림차순 정렬
    List<BoardEntity> findByBoardTitleContainingOrderByIdDesc(String q);

    //작성자 또는 제목에 검색어가 포함된 결과 조회
    // select * from board_table where board_title like '%q%' or board_writer like '%q%';
    List<BoardEntity> findByBoardTitleContainingOrBoardWriterContainingOrderByIdDesc(String q1,String q2);
}
