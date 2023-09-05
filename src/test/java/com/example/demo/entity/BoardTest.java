package com.example.demo.entity;

import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional  // 테스트가 성공해도 롤백을 하게 됨 -> 말 그대로 기능 확인만 하고, 테스트로 생성한 값들을 저장하지 않고 초기화시켜야 하기 떄문
// @TestPropertySource(properties = {"spring.config.location=classpath:application-test.yml"})
class BoardTest {

//    @Autowired
//    private BoardService boardService;
//
//    // 더미데이터 생성용
//    private BoardDto createBoardDto() {
//        // 값을 담을 DTO 생성
//        BoardDto boardDto = new BoardDto();
//        // DTO의 필드에 맞는 값을 직접 기입
//        boardDto.setTitle("test1");
//        boardDto.setWriter("bulgomi");
//        // Board 엔티티
//        return boardDto;
//    }

//    @Test
//    @DisplayName("게시글 저장 테스트")  // 테스트 로그에 남음, 주석 정도의 의미
//    public void saveTest() {
////        Board savedBoard = boardService.saveBoard(저장할 데이터);
//        BoardDto boardDto = createBoardDto();
//        Board savedBoard = boardService.saveBoard(boardDto);
//
//        // 두 값을 비교해서 같을 경우 테스트가 정상적으로 돌아갔다고 알려주는 메서드
//        assertEquals(boardDto.getTitle(), savedBoard.getTitle());
//        assertEquals(boardDto.getWriter(), savedBoard.getWriter());
//    }

}