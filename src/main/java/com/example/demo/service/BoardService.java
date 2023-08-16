package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.entity.Member;
import com.example.demo.repository.BoardRepository;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional  // 오류가 났을 때 롤백을 시키거나 정상적으로 이루어질 때 반영할 수 있도록 원자성을 부여함
@RequiredArgsConstructor    // 쓰지 않으면 @Autowired를 사용함
public class BoardService {

    private final BoardRepository boardRepository;

    private final MemberRepository memberRepository;

    // 값 저장
    public Board saveBoard(BoardDto boardDto, String email) {

        Member member = memberRepository.findByEmail(email);
        boardDto.setWriter(member.getName());

        // save() 메서드는 내가 준 엔티티가 컨텍스트에 잘 반영됐는지 객체로 반환해주는 기능을 함
        // 저장은 엔티티만 가능
        return boardRepository.save(Board.createBoard(boardDto, member));
    }

    // 게시물 리스트 뽑기
    public Page<Board> getBoardList(Pageable pageable) {

        return boardRepository.findAll(pageable);

//        // Pageable 사용 시 불필요
//        // 제네릭에다가 리스트에 BoardDto를 담을 거라고 명시함
//        List<BoardDto> boardDtos = new ArrayList<>();
//
//        // 가져온 소스를 모두 살펴봐야 할 때 사용
//        for (Board board : boardRepository.findAll()) {
//            boardDtos.add(BoardDto.of(board));
//
//        // 같은 기능
//        // 특정 부분만 살펴보거나 해야 할 때 사용
//        List<Board> boards = boardRepository.findAll();
//        for (int i=0; i<boards.size(); i++) {
//            boardDtos.add(BoardDto.of(boards.get(i)));
//        }
//
//        return boardDtos;
    }

    // 게시물 상세 조회
    public BoardDto showDetail(Long boardId) {

        // board의 id값을 찾음
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityExistsException::new);   // :: -> 클래스 내 메서드 추출 개념
        return BoardDto.of(board);
    }

    // 게시물 삭제
    public void deleteBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(EntityExistsException::new);
        boardRepository.delete(board);
    }

    // 게시물 수정
    public void updateBoard(BoardDto boardDto) {
        Board board = boardRepository.findById(boardDto.getId())
                .orElseThrow(EntityExistsException::new);
        board.updateBoard(boardDto);
    }

}
