package com.example.demo.entity;

import com.example.demo.auditing.BaseEntity;
import com.example.demo.dto.ReplyDto;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "REPLY")
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Reply extends BaseEntity {

    @Id
    @Column(name = "REPLY_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String writer;

    @ManyToOne  // 하나의 게시글에 여러 댓글이 달릴 수 있음
    @JoinColumn(name = "BOARD_ID")
    @OnDelete(action = OnDeleteAction.CASCADE)  // 부모 게시글과 연관된 자식 댓글을 삭제함
    private Board board;

    @ManyToOne  // 하나의 멤버가 여러 게시글에 달 수 있음
    @JoinColumn(name = "MEMBER_EMAIL")
    private Member member;

    @Builder
    Reply(String content, String writer, Board board, Member member) {
        this.content = content;
        this.writer = writer;
        this.board = board;
        this.member = member;
    }

    public static Reply createReply(ReplyDto replyDto, Member member, Board board) {
        return com.example.demo.entity.Reply.builder()
                .content(replyDto.getContent())
                .writer(replyDto.getWriter())
                .member(member)
                .board(board)
                .build();
    }

    public void updateReply(String content) {
        this.content = content;
    }
}
