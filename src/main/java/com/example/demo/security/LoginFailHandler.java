package com.example.demo.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class LoginFailHandler extends SimpleUrlAuthenticationFailureHandler { // 로그인 버튼 클릭 시 발생할 수 있는 에러를 다룸

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        logger.info("login fail handler");

        String errorMessage;
        if (e instanceof UsernameNotFoundException){
            errorMessage = "존재하지 않는 아이디 입니다.";
        }else if (e instanceof BadCredentialsException || e instanceof InternalAuthenticationServiceException){
            errorMessage = "아이디 또는 비밀번호가 맞지 않습니다."; // 관습적인 부분. '어느 하나가 틀렸다' 라고 특정하지 않음.
        }
        else{
            errorMessage = "알 수 없는 이유로 로그인이 되지 않습니다.\n";
            errorMessage += e.getMessage(); // 에러 로그를 담아줌
        }

        // 에러 메세지를 담아서 보냄
        errorMessage= URLEncoder.encode(errorMessage,"UTF-8");//한글 인코딩 깨지는 문제 방지
        setDefaultFailureUrl("/member/login/error?error=true&exception=" + errorMessage);
        super.onAuthenticationFailure(request,response,e);  // 상속 받은 부모 클래스에서 기능을 가져옴
    }


}
