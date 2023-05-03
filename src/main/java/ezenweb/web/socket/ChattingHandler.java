package ezenweb.web.socket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

//* 서버 소켓
@Component //빈등록, 스프링이 해당 클래스를 관리하기 위해서 => 제어역전(IoC)
@Slf4j //로그 찍기
public class ChattingHandler extends TextWebSocketHandler {
    
    //0. ** 서버소켓에 접속한 명단 저장 WebSocketSession : 스프링꺼 사용
    private static List<WebSocketSession> connectionList = new ArrayList<>(); //접속 명단
    
    //1. 클라이언트가 서버소켓으로 접속했을 때
    @Override 
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        log.info("afterConnectionEstablished : " + session);
        connectionList.add(session); //클라이언트 세션에 들어왔을 때 리스트에 추가[다른 세션들과 통신하기 위해]
    }

    //2. 클라이언트로 부터 메시지 받았을 때
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("handleTextMessage : " + session);

        for(WebSocketSession conn : connectionList) {
            conn.sendMessage(message);
        }
    }

    //3. 클라이언트가 서버 소켓으로 부터 나갔을 때
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        log.info("afterConnectionClosed : " + session);
        connectionList.remove(session); //클라이언트 세션이 나갔을 때 리스트 제거
    }
}
