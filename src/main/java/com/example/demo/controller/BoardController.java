package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    // 게시글 조회
    @GetMapping(value = "/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model) { // uri에 변수로 받을 값을 @PathVariable로 받아옴

        model.addAttribute("boardDto", boardService.showDetail(boardId));

        return "/pages/boards/boardDetail";
    }

}
