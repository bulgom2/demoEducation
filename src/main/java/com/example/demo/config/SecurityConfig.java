package com.example.demo.config;

import com.example.demo.security.LoginFailHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

// 시큐리티를 추가하는 순간 검증절차가 포함됨
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    //
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable();

        httpSecurity.formLogin()
                .loginPage("/member/login")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .failureHandler(loginFailHandler())
//                .failureUrl()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutSuccessUrl("/member/login");

        // .permitAll() => 루트에는 모든 권한을 허용함 (로그인 안 해도 들어옴)
        // .hasAnyRole(~, ~) => 특정 권한만 허용함 (로그인 안 한 사람은 못 들어옴)
        // 루트에서 밑으로 하향식으로 내려감
        httpSecurity.authorizeRequests()
                .mvcMatchers("/").permitAll()
                .mvcMatchers("/member").permitAll()
                .mvcMatchers("/board/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().permitAll();

        return httpSecurity.build();
    }

    @Bean
    public LoginFailHandler loginFailHandler() {
        return new LoginFailHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
