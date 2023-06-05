#### BoardDTO 의 toDTO 메서드를 builder 패턴으로 사용해보기
```javascript
    public static BoardDTO toDTO(BoardEntity boardEntity) {
        // 빌더 패턴
       return BoardDTO.builder()
               .id(boardEntity.getId())
               .boardWriter(boardEntity.getBoardWriter())
               .boardPass(boardEntity.getBoardPass())
               .boardTitle(boardEntity.getBoardTitle())
               .boardContents(boardEntity.getBoardContents())
               .boardHits(boardEntity.getBoardHits())
               .createdAt(boardEntity.getCreatedAt())
               .build();
    }
```
#### BoardService 에서 findAll사용할 때 기타 폴문을 사용할때 새로운 방식
```javascript
public List<BoardDTO> findAll() {
        List<BoardDTO> boardDTOList = new ArrayList<>();
        boardRepository.findAll().forEach(boardEntity -> {
            boardDTOList.add(BoardDTO.toDTO(boardEntity));
        });
        return boardDTOList;
    }
```