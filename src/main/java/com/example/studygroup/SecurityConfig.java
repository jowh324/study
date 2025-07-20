package com.example.studygroup;

import com.example.studygroup.Service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
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
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;


    public SecurityConfig(CustomUserDetailsService uds) {
        this.userDetailsService = uds;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((auth) -> auth
                        // 웹소켓 연결 경로 및 SockJS 관련 경로 허용
                       // .requestMatchers("/ws-chat/**").permitAll()
                        .requestMatchers("/index.html", "/signup.html").permitAll()

                        .requestMatchers("/api/users/signup", "/api/users/login").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1) // 1. 한 사용자당 최대 세션 수를 1로 제한
                        .maxSessionsPreventsLogin(false) // 2. 새로운 로그인이 발생하면 기존 세션을 만료시킴 (true로 하면 새로운 로그인 차단)
                )
                .formLogin((form) -> form
                        // Spring Security가 로그인 요청을 처리할 URL
                        .loginProcessingUrl("/api/users/login")
                        // 로그인 ID로 사용할 파라미터 이름
                        .usernameParameter("email")
                        // 로그인 성공 시 핸들러
                        .defaultSuccessUrl("/api/boards", true)
                        // 로그인 실패 시 핸들러
                        .failureHandler((request, response, exception) -> {
                            response.setStatus(401);
                            response.getWriter().write("Login Failed: " + exception.getMessage());
                        })
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/api/users/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(200);
                            response.getWriter().write("Logout Successful");
                        })
                );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder()); // Bean으로 등록된 passwordEncoder() 사용
        return provider;
    }
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("http://api.studyplan.p-e.kr"));
        // 모든 출처 허용 x
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(Arrays.asList("*")); // 허용할 HTTP 헤더
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // 모든 경로에 CORS 설정 적용
        return source;
    }

    // AuthenticationManager는 AuthenticationConfiguration에서 가져오기
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean<>(new HttpSessionEventPublisher());
    }
}
