package com.example.demo.entity;

import com.example.demo.dto.BoardDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "BOARD")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Board {

    @Id
    @Column(name = "BOARD_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String writer;

    @Builder
    Board(String title, String writer) {
        this.title = title;
        this.writer = writer;
    }

    // 서버 실행 후 프로그램이 로드되고 컴파일되고 난 시점에 바로 쓰기 위해 static을 사용 -> 객체 생성(인스턴스)을 하지 않아도 사용 가능함
    // 생성자로 만들어서 형체가 있으면 인스턴스라고 할 수 있음
    // static이 없으면 인스턴스 메서드라고 함
    // 인스턴스가 생성된 시점에 그 인스턴스를 통해서 사용할 수 있게 만드는 메서드
    // 클래스만 메모리에 로드하면
    // 인스턴스
    // 속성(필드)과 기능(메서드)을 가진 덩어리 = 객체
    // 추상적으로 정의한 객체를 만들 수 있도록 정의한 설계도 = 클래스

    // Board를 생성하기 위한 메서드인데 서버 실행 후 Board를 생성하고 나서 Board를 생성하기 위한 메서드를 사용하는 것은 말이 안 됨
    // 그래서 static을 사용하여 서버 실행 시 바로 Board를 생성할 수 있는 메서드로 정의함
    public static Board createBoard(BoardDto boardDto) {
        // 위에 선언한 생성자의 인자의 순서 상관 없이, 넣고 싶은 것만 값을 넣을 수 있도록 하는 메서드
        // 빌더는 값 변환만 해주는 기능 -> 저장은 서비스 단에서
        return Board.builder()
                .title(boardDto.getTitle())
                .writer(boardDto.getWriter())
                .build();
    }

}