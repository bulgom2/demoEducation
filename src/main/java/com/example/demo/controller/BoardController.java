package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.ReplyDto;
import com.example.demo.service.BoardService;
import com.example.demo.service.ReReplyService;
import com.example.demo.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;
    private final ReReplyService reReplyService;

    // 게시글 목록 & 페이징 버튼
    @GetMapping(value = "/info")
    public String boardInfo(@RequestParam(value = "page", required = false, defaultValue = "0") String page,
                            Model model) {
        Pageable pageable = PageRequest.of(Integer.parseInt(page),5);
        model.addAttribute("page", boardService.getBoardList(pageable));
        return "pages/boards/boardInfo";
    }

    // 페이지 이동
    @PostMapping(value = "/info/{page}")
    public String boardPages(@PathVariable Integer page, Model model) {
        Pageable pageable = PageRequest.of(page, 5);
        model.addAttribute("page", boardService.getBoardList(pageable));
        return "pages/boards/pageCard";
    }

    @GetMapping(value = "/form")
    public String boardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "pages/boards/boardForm";
    }

    // @Valid의 타입 검증 후, 부합하지 않으면 BindingResult에 DTO에서 넘긴 정보(+문구)를 담음
    @PostMapping(value = "/form")
    public String boardSave(@Valid BoardDto boardDto, BindingResult bindingResult,
                            Authentication authentication, Model model) {

        String email = authentication.getName();

        // BindingResult가 에러를 가지고 있으면
        if (bindingResult.hasErrors()) {
            return "pages/boards/boardForm";
        }
        // service 호출
        boardService.saveBoard(boardDto, email);
        // dto 넘겨주고 service에서는 repository.save()
        return "redirect:/board/info";
    }

    // 게시물 조회
    // uri에 변수로 받을 값을 @PathVariable로 받아옴
    // Authentication에는 요청된 사용자의 정보가 담김
    @GetMapping(value = "/{boardId}")
    public String boardDetail(@PathVariable Long boardId, Model model, Authentication authentication) {

        String userEmail = authentication.getName();
        BoardDto boardDto = boardService.showDetail(boardId);
        List<ReplyDto> replyDtoList = replyService.getReplyList(boardId);
        for (ReplyDto r : replyDtoList) {
            r.setReReplyDtoList(reReplyService.getReReplies(r.getId()));
        }

        model.addAttribute("userEmail", userEmail);
        model.addAttribute("boardDto", boardDto);
        model.addAttribute("replies", replyDtoList);

        return "pages/boards/boardDetail";
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
