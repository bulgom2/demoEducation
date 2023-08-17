package com.example.demo.service;

import com.example.demo.dto.ReReplyDto;
import com.example.demo.entity.Member;
import com.example.demo.entity.ReReply;
import com.example.demo.entity.Reply;
import com.example.demo.repository.MemberRepository;
import com.example.demo.repository.ReReplyRepository;
import com.example.demo.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReReplyService {

    private final ReplyRepository replyRepository;

    private final MemberRepository memberRepository;

    private final ReReplyRepository reReplyRepository;

    public Long saveReReply(Long replyId, String content, String email) {

        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(EntityExistsException::new);

        Member member = memberRepository.findByEmail(email);

        ReReply reReply = ReReply.builder()
                .reply(reply)
                .member(member)
                .writer(member.getName())
                .content(content)
                .build();

        reReplyRepository.save(reReply);

        return reply.getBoard().getId();
    }

    public List<ReReplyDto> getReReplies(Long replyId) {

        Reply reply = replyRepository.findById(replyId)
                .orElseThrow(EntityExistsException::new);

        List<ReReplyDto> result = new ArrayList<>();

        for (ReReply reReply : reReplyRepository.findByReply(reply)) {
            result.add(ReReplyDto.of(reReply));
        }

        return result;
    }

    public void deleteReReply(Long reReplyId) {
        reReplyRepository.deleteById(reReplyId);
    }

    public Long updateReReply(Long reReplyId, String content) {
        ReReply reReply = reReplyRepository.findById(reReplyId)
                .orElseThrow(EntityExistsException::new);
        reReply.updateReReply(content);
        return reReply.getReply().getBoard().getId();
    }

}
