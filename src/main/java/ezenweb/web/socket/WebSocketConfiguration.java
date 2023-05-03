package ezenweb.web.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//* 소켓 매핑을 잡아줌
@Configuration // 컴포넌트 등록 [주요 : Service , Controllerm Repository 등등]
@Slf4j
@EnableWebSocket // * 웹 소켓 연결 WSA 프로토콜, WS 프로토콜의 URL 매핑 연결
public class WebSocketConfiguration implements WebSocketConfigurer { //implments : 구현 하다

    //스프링 빈에 등록되는것은 하나이다(같은 객체일 경우) => 싱글톤을 쓸 필요가 없다 => 빈을 사용하기 때문!
    //=> 스프링이 객체를 관리해주고, 서로 다른 객체가 아닌 같은 객체를 공유하는 방식 => 메모리 효율적!!!
    //즉,  ChattingHandler ch = new ChattingHandler(); 이런식으로 객체를 생성하는 것은 서로 다른 객체를 계속헤서 만드는 것이기 때문에 메모리 낭비가 심하다.
    @Autowired //컴포넌트에 등록한 클래스 이므로 @Autowired 가능
    private  ChattingHandler chattingHandler; //의존성 주입(DI)

    @Override //서버 소켓으로 사용되고 있는 클래스를 등록(프론트 주소(라우터)와 겹치면 절대 안됨!!!!!)
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //chatHandler를 chat이라는 주소로 매핑하며, 들어올 수 있는 도메인은 모두("*")이다.
        registry.addHandler(chattingHandler, "/chat").setAllowedOrigins("*");
        //registry.addHandler(서버소켓 객체, "서버소켓의 path/url").setAllowedOrigins("*"); : 서버소켓 등록 함수
            //.setAllowedOrigins("접속허용 도메인") : 해당 서버소멧으로 부터 요청할 수 있는 URL/도메인
            //.setAllowedOrigins("*") : 해당 서버소멧으로 부터 요청할 수 있는 URL/도메인

        //서버 소켓이 더 있으면 의존성 주입 객체를 만들고 위처럼 해당 객체로 더 추가하면된다.

    }
}
