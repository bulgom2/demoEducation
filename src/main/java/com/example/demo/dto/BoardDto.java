package com.example.demo.dto;

import com.example.demo.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {

    // 데이터 타입은 객체 타입(래퍼 클래스)으로 쓰는 것이 좋음

    private Long id;

    @NotBlank(message = "제목을 써야죠??? 🙂") // Controller의 BindingResult에 담길 내용
    private String title;

    @NotBlank(message = "내용도 제대로 쓰실게요??? 🙏🙏")
    private String content;


    private String writer;

    // @Getter, @Setter 필수
    private static ModelMapper modelMapper = new ModelMapper();

    public static BoardDto of(Board board) {
        return modelMapper.map(board, BoardDto.class);
    }

}
