package com.example.demo.entity;

import com.example.demo.auditing.BaseEntity;
import com.example.demo.dto.ReReplyDto;
import com.example.demo.dto.ReplyDto;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "RE_REPLY")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class ReReply extends BaseEntity {

    @Id
    @Column(name = "RE_REPLY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "REPLY_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)  // 부모와 연관된 자식 요소들을 모두 삭제함
    private Reply reply;

    @Column(nullable = false)
    private String writer;

    @ManyToOne  // 하나의 멤버가 여러 게시글에 달 수 있음
    @JoinColumn(name = "MEMBER_EMAIL")
    private Member member;

    @Builder
    ReReply(String content, String writer, Reply reply, Member member) {
        this.content = content;
        this.writer = writer;
        this.reply = reply;
        this.member = member;
    }

    public static ReReply createReReply(ReReplyDto reReplyDto, Member member, Reply reply) {
        return ReReply.builder()
                .content(reReplyDto.getContent())
                .writer(reReplyDto.getWriter())
                .member(member)
                .reply(reply)
                .build();
    }

    public void updateReReply(String content) {
        this.content = content;
    }
}
