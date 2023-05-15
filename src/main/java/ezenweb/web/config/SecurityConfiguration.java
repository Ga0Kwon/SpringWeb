package ezenweb.web.config;

import ezenweb.web.controller.AuthSuccessFailHandler;
import ezenweb.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration // Spring Bean에 등록 [MVC 컴포넌트]
// WebSecurityConfigurerAdapter  : 시큐리티 설정 연결 클래스
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberService memberService;

    @Autowired
    private AuthSuccessFailHandler authSuccessFailHandler;

    // 인증[로그인] 관련 보안 담당 메서드
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(memberService).passwordEncoder(new BCryptPasswordEncoder());
        // auth.userDetailsService(); : UserDetailsService 구현된 서비스 대입
        // .passwordEncoder(new BCryptPasswordEncoder()) : 서비스 안에서 로그인시 패스워드 검증시 사용된 암호화 객체 대입
    }

    //configure(HttpSecurity http) : http[URL] 관련 보안 담당
    @Override // 재정의 [ 코드 바꾸기 ]
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http); //super : 부모클래스 호출 => 이거 주석처리하면 첫 로그인 화면
        // 부모꺼 안써 => 사이트 열림
        http
                .authorizeHttpRequests() //1. 인증[권한]에 따른 http 요청 제한
                    .antMatchers("/admin/**").hasRole("ADMIN") //admin 관련 페이지는 admin만
                    //.antMatchers("/board/update").hasAnyRole("USER", "ADMIN") //권한 여러개ㅔ 넣을 때
                    .antMatchers("/board/update").hasRole("USER")//수정 회원제
                    .antMatchers("/board/delete").hasRole("USER") //삭제 회원제
                    .antMatchers("/board/write").hasRole("USER") // 글쓰기 회원제
                    .antMatchers("/**").permitAll() //나머지는 권한X
                .and()
                //.csrf().ignoringAntMatchers() => 하나하나 여는 경우
                .formLogin()
                    .loginPage("/member/login") // 로그인으로 사용될 페이지 매핑 URL[어떤 페이지에서 로그인하는지]
                    .loginProcessingUrl("/member/login") // 로그인을 처리할 매핑 URL
                    //.defaultSuccessUrl("/") // 로그인 성공했을 때 이동하는 매핑 URL[성공시 index페이지]
                    .successHandler(authSuccessFailHandler)
                    //.failureUrl("/member/login") //로그인실패했을때 이동할 매핑 URL [실패시 제자리]
                    .failureHandler(authSuccessFailHandler) // ���그인�����
                    .usernameParameter("memail") // 로그인시 사용될 계정 아이디의 필드명
                    .passwordParameter("mpassword") //로그인시 사용될 계정 패스워드의 필드명
                .and()
                    .logout()
                        .logoutRequestMatcher( new AntPathRequestMatcher("/member/logout")) //로그아웃 처리를 요청할 매핑 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공했을 때 이동할 매핑 URL
                        .invalidateHttpSession(true) //세션 초기화x
                .and()
                    .oauth2Login()  // oauth2  로그인 => 소셜 로그인 쓰겠다. /oauth2/authorization/클라이언트로 되어있는 것들은 다 여기서 채감
                    .defaultSuccessUrl("/") // 로그인 성공시 이동할 매핑 URL
                    //.successHandler(authSuccessFailHandler)
                    .userInfoEndpoint() // 스프링 시큐리티로 들어올 수 있도록 시큐리티 로그인 엔드포인트[종착점]
                    .userService(memberService); //oauth2 서비스를 지원하는

        http.cors(); //CORS 정책 사용.
        http.csrf().disable(); //CSRF 사용 해제

    } //configure


    //import org.springframework.web.cors.CorsConfigurationSource;
    //스프링 시큐리티에 CORS 정책 설정 [리액트가 요청 받기 위해]
/*    @Bean //빈 등록
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); //주소
        configuration.setAllowedMethods(Arrays.asList("HEAD", "PUT", "POST", "DELETE", "GET")); //http 메소드
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Cache-Control", "Authorization"));// http 설정
        configuration.setAllowCredentials(true); //토큰 전송 할 수 있게
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }*/

}
