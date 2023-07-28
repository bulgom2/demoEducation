package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional  // 오류가 났을 때 롤백을 시키거나 정상적으로 이루어질 때 반영할 수 있도록 원자성을 부여함
@RequiredArgsConstructor    // 쓰지 않으면 @Autowired를 사용함
public class BoardService {

    private final BoardRepository boardRepository;

    // 값 저장
    public Board saveBoard(BoardDto boardDto) {

        // save() 메서드는 내가 준 엔티티가 컨텍스트에 잘 반영됐는지 객체로 반환해주는 기능을 함
        // 저장은 엔티티만 가능
        return boardRepository.save(Board.createBoard(boardDto));
    }

    // 게시물 조회
    public List<BoardDto> getBoardList() {

        // 제네릭에다가 리스트에 BoardDto를 담을 거라고 명시함
        List<BoardDto> boardDtos = new ArrayList<>();

        // 가져온 소스를 모두 살펴봐야 할 때 사용
        for (Board board : boardRepository.findAll()) {
            boardDtos.add(BoardDto.of(board));
        }

//        // 같은 기능
//        // 특정 부분만 살펴보거나 해야 할 때 사용
//        List<Board> boards = boardRepository.findAll();
//        for (int i=0; i<boards.size(); i++) {
//            boardDtos.add(BoardDto.of(boards.get(i)));
//        }

        return boardDtos;
    }

}
