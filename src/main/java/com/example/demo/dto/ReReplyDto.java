package com.example.demo.dto;

import com.example.demo.entity.ReReply;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReReplyDto {

    private Long id;

    private Long replyId;

    private String content;

    private String writer;

    private String memberEmail;

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


    private static ModelMapper modelMapper = new ModelMapper();

    public static ReReplyDto of(ReReply reReply) {
        return modelMapper.map(reReply, ReReplyDto.class);
    }
}
