package com.example.demo.service;

import com.example.demo.dto.MemberFormDto;
import com.example.demo.entity.Member;
import com.example.demo.repository.MemberRepository;
import com.example.demo.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    // 컨트롤러로 떠넘김
    public void saveMember(MemberFormDto memberFormDto) {
        validateMember(memberFormDto);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        memberRepository.save(member);
    }

    // throw => 내가 발생시킨 예외를 생성시킴
    // throws => 그 발생시킨 예외를 떠넘김
    // 위로 떠넘김
    private void validateMember(MemberFormDto memberFormDto) throws IllegalStateException {
        Member member = memberRepository.findByEmail(memberFormDto.getEmail());
        if (member != null) {
            throw new IllegalStateException("이미 사용 중인 이메일 주소 입니다.");
        }
    }

    // 없는 유저 확인
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email);

        if (member == null) {
            throw new UsernameNotFoundException(email);
        }

        return new CustomUserDetails(member);
    }
}
