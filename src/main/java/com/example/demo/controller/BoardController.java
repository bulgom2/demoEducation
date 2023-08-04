package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import jdk.net.SocketFlow;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping(value = "/info")
    public String boardInfo(Model model) {
        model.addAttribute("boards", boardService.getBoardList());
        return "/pages/boards/boardInfo";
    }

    @GetMapping(value = "/form")
    public String boardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "/pages/boards/boardForm";
    }

    // @Valid의 타입 검증 후, 부합하지 않으면 BindingResult에 정보를 담음
    @PostMapping(value = "/form")
    public String boardSave(@Valid BoardDto boardDto, BindingResult bindingResult) {

        // BindingResult가 에러를 가지고 있으면
        if (bindingResult.hasErrors()) {
            return "/pages/boards/boardForm";
        }
        // service 호출
        boardService.saveBoard(boardDto);
        // dto 넘겨주고 service에서는 repository.save()
        return "redirect:/board/info";
    }

    // 게시물 조회
    @GetMapping(value = "/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model) { // uri에 변수로 받을 값을 @PathVariable로 받아옴

        model.addAttribute("boardDto", boardService.showDetail(boardId));

        return "/pages/boards/boardDetail";
    }

    // 게시물 삭제
    @DeleteMapping(value = "/delete/{boardId}")
    public ResponseEntity boardDelete(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return new ResponseEntity<Long>(boardId, HttpStatus.OK);
    }

    // 게시물 수정   // 수정 시 절대 엔티티에 그대로 Setter로 하지 않음
    @PatchMapping(value = "/update")
    public ResponseEntity<Long> boardUpdate(@RequestBody BoardDto boardDto) {   // 헤더와 바디를 모두 포함하는 타입
        boardService.updateBoard(boardDto);
        return new ResponseEntity<Long>(boardDto.getId(), HttpStatus.OK);   // == @ResponseBody
    }

}
