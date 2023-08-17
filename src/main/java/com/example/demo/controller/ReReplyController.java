package com.example.demo.controller;

import com.example.demo.service.ReReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/reReply")
@RequiredArgsConstructor
public class ReReplyController {

    private final ReReplyService reReplyService;

    @PostMapping(value = "/new")
    public String createReReply(@RequestParam Long replyId,
                                @RequestParam String reReply,
                                Authentication authentication) {

        Long boardId = reReplyService.saveReReply(replyId, reReply, authentication.getName());

        return "redirect:/board/" + boardId;
    }

    @DeleteMapping(value = "/delete/{reReplyId}")
    public ResponseEntity<Long> deleteReReply(@PathVariable Long reReplyId) {
        reReplyService.deleteReReply(reReplyId);
        return new ResponseEntity<Long>(reReplyId, HttpStatus.OK);
    }

    @PostMapping(value = "/update")
    public String updateReReply(@RequestParam Long reReplyId,
                                @RequestParam String reReply) {

        Long boardId = reReplyService.updateReReply(reReplyId, reReply);

        return "redirect:/board/" + boardId;
    }
}
