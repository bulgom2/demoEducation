package com.example.demo.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {

    // 괄호 안에 원하는 값들을 쭉 쓰고, 아래에 개수와 변수에 맞게 선언해주면 됨, @RAC 어노테이션 필수
    USER("ROLE_USER", "일반 사용자"), ADMIN("ROLE_ADMIN", "관리자");

    private final String key;
    private final String title;

}
