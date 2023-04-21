package ezenweb.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import ezenweb.example.day06.객체관계.Member;
import ezenweb.web.domain.member.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//AuthenticationSuccessHandler : 인증이 성공했을 때에 대한 인터페이스
// onAuthenticationSuccess : 인ㄴ증이 성공했을 때 실행되는 정보

//AuthenticationFailureHandler : 인증이 실패헸을 때에 대한 인터페이스
// onAuthenticationFailure : 인증증이 실패했을 때 실행되는 정보

@Component
@Slf4j //로그 기능 주입
// 인증 성공했을 때와 실패 했을 때 시큐리티 컨트롤 재구현
public class AuthSuccessFailHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

    //인수 : @reqest @response @authentication : 인증 성공한 정보

    //ObjectMapper
    //@Autowired : spring에서 제공하는 것이 아니기 때문에 사용불가
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("authentication success" + authentication.toString());
        MemberDto dto = (MemberDto)authentication.getPrincipal(); //로그인 성공한 객체
        String json = objectMapper.writeValueAsString(dto); //objectMapper가 locldateTime형식을 지원하지 않는다.

        //AJAX에게 전달
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json"); //@responseBody 사용안할 때 직접 작용
        response.getWriter().print(json);
    }

    
    //인수 : @reqest @response @authentication : 예외[인증 실패한 예외 객체 ??????????]
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("authentication failure" + exception.toString());
        String json = objectMapper.writeValueAsString(false);
        //AJAX에게 전달
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json"); //@responseBody 사용안할 때 직접 작용
        response.getWriter().print(json);
    }
}
