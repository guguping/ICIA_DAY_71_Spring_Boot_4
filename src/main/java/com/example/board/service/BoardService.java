package com.example.board.service;

import com.example.board.dto.BoardDTO;
import com.example.board.dto.CommentDTO;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import com.example.board.entity.CommentEntity;
import com.example.board.repository.BoardFileRepository;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.CommentRepository;
import com.example.board.uil.UtilClass;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    public void save(BoardDTO boardDTO) throws IOException {
        if (boardDTO.getBoardFile() ==null || boardDTO.getBoardFile().get(0).isEmpty()) {
            boardRepository.save(BoardEntity.toSaveEntity(boardDTO));
        } else {
            // 파일 있음
            // 1.board 테이블에 데이터를 먼저 저장
            BoardEntity boardEntity = BoardEntity.toSaveEntityWithFile(boardDTO);
            BoardEntity savedEntity = boardRepository.save(boardEntity);
            List<String> newfile = new ArrayList<>();
            // 2. 파일이름 꺼내고, 저장용 이름 만들고 파일 로컬에 저장
            for (MultipartFile boardFile : boardDTO.getBoardFile()) {
                String originalFileName = boardFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
                String savePath = "D:\\springboot_img\\" + storedFileName;
                boardFile.transferTo(new File(savePath));
                // 3. BoardFileEntity로 변환 후 board_file_table에 저장
                // 자식 데이터를 저장할 때 반디시 부모의 id가 아닌 부모의 Entity 객체가 전달돼야 함.
                BoardFileEntity boardFileEntity = BoardFileEntity.toSaveBoardFileEntity(savedEntity, originalFileName, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }
        }
    }

    @Transactional
    public List<BoardDTO> findAll() {
        List<BoardDTO> boardDTOList = new ArrayList<>();
//        for (BoardEntity boardEntity : boardRepository.findAll()) {
//            boardDTOList.add(BoardDTO.toDTO(boardEntity));
//        }
        boardRepository.findAll().forEach(boardEntity -> {
            boardDTOList.add(BoardDTO.toDTO(boardEntity));
        });
        return boardDTOList;
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    @Transactional
    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return BoardDTO.toDTO(boardEntity);
    }

    public void boardUpdate(BoardDTO boardDTO) {
        boardRepository.save(BoardEntity.toUpdate(boardDTO));
    }

    public void boardDelete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable , String type , String q) {
        int page = pageable.getPageNumber() -1;
        int pageLimit = 5;
        Page<BoardEntity> boardEntities = null;
        if(type.equals("title")){
            boardEntities = boardRepository.findByBoardTitleContaining(q,PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else if (type.equals("writer")){
            boardEntities = boardRepository.findByBoardWriterContaining(q,PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        } else {
            boardEntities = boardRepository.findAll(PageRequest.of(page,pageLimit, Sort.by(Sort.Direction.DESC,"id")));
        }
        // page 몇페이지를 볼건지 , pageLimit 몇개를 보여줄건지 , Sort 어떤 기준으로 보여줄건지
        Page<BoardDTO> boardDTOS = boardEntities.map(boardEntity -> BoardDTO.builder()
                .id(boardEntity.getId())
                .boardTitle(boardEntity.getBoardTitle())
                .boardWriter(boardEntity.getBoardWriter())
                .createdAt(UtilClass.dateFormat((boardEntity.getCreatedAt())))
                .boardHits(boardEntity.getBoardHits())
                .build());
        return boardDTOS;
    }
}
