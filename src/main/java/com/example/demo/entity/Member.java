package com.example.demo.entity;

import com.example.demo.constant.Role;
import com.example.demo.dto.MemberFormDto;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@ToString
@NoArgsConstructor
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)    // 내부에서는 Enum이지만 외부에서는 String으로 취급하게 함
    private Role role;

    public static Member createmember(MemberFormDto memberFormDto, PasswordEncoder passwordEncoder) {
        return Member.builder()
                .name(memberFormDto.getName())
                .email(memberFormDto.getEmail())
                .password(memberFormDto.getPassword())
                .role(Role.USER)
                .build();
    }

    // 권한 반환 메소드
    public String getRoleKey() {
        return this.role.getKey();
    }

    @Builder
    public Member(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }





}
