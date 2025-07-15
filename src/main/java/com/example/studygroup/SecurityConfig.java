package com.example.studygroup;

import com.example.studygroup.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(CustomUserDetailsService uds, PasswordEncoder pe) {
        this.userDetailsService = uds;
        this.passwordEncoder    = pe;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 1) CSRF 비활성화
                .csrf(csrf -> csrf.disable())

                // 2) 인가 규칙
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/users/signup", "/api/users/login")
                        .permitAll()
                        .anyRequest().authenticated()
                )

                // 3) HTTP Basic 로그인 방식 활성화
                .httpBasic(Customizer.withDefaults())

                // 4) 세션 정책 (STATeless)
                .sessionManagement(sm -> sm
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // 5) DAO 인증 프로바이더 등록
                .authenticationProvider(daoAuthenticationProvider());

        return http.build();
    }

    // DAO 인증 프로바이더를 빈으로 등록
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    // AuthenticationManager는 AuthenticationConfiguration에서 가져오기
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
